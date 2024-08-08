<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <div>
            <el-row style="background-color: rgba(255,255,255);margin-top: 10px">
                <!--周月年的选择-->
                <el-col :span="4">
                    <el-dropdown @command="handleCommand">
                        <el-button type="primary">
                            {{ selectedName }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu>
                            <el-dropdown-item command="week">每周</el-dropdown-item>
                            <el-dropdown-item command="month">每月</el-dropdown-item>
                            <el-dropdown-item command="year">每年</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
                <!-- 日期的选择 -->
                <el-col :span="10">
                    <el-date-picker
                            v-model="$store.state.datePicker"
                            :type="$store.state.datePickerType"
                            :format="datePickerFormat"
                            :placeholder="datePickerPlaceholder"
                            @change="timeChanged">
                    </el-date-picker>
                </el-col>
                <!-- 统计值：收入 -->
                <el-col :span="3">
                    <div>
                        <el-statistic :precision="2" :value="this.$store.state.totalIncome" title="收入"></el-statistic>
                    </div>

                </el-col>
                <!-- 统计值：支出 -->
                <el-col :span="3">
                    <div>
                        <el-statistic :precision="2" :value="this.$store.state.totalExpenditure"
                                      title="支出"></el-statistic>
                    </div>
                </el-col>
                <!-- 添加新的账单记录 -->
                <el-col :span="4">
                    <el-button icon="el-icon-circle-plus-outline" style="font-size: 22px;" @click="handleAdd">添加
                    </el-button>
                </el-col>
            </el-row>
        </div>
        <el-main class="main">
            <div style="margin-top: 25px">
                <template>
                    <el-table :data="tableData" ref="table" height="520" style="width:100%;">
                        <el-table-column type="index" label="编号" align="center">
                            <template slot-scope="scope">
                                <!-- 在插槽中自定义索引的显示 -->
                                <span>{{ scope.$index + (currentPage - 1) * pageSize + 1 }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="日期" prop="date" align="center"></el-table-column>
                        <el-table-column label="种类" prop="kind" align="center">
                            <template slot-scope="scope">
                                <span>{{ scope.row.kind }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="类别" prop="bill_type" align="center"></el-table-column>
                        <el-table-column label="金额" prop="amount" align="center"></el-table-column>
                        <el-table-column label="操作" align="center">
                            <template slot-scope="scope">
                                <el-button
                                        size="mini"
                                        @click="handleEdit(scope.$index, scope.row)">编辑
                                </el-button>
                                <el-button
                                        size="mini"
                                        type="danger"
                                        @click="handleDelete(scope.$index, scope.row)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column label="备注" prop="info"></el-table-column>
                    </el-table>
                    <!-- 分页组件 -->
                    <el-pagination :page-size="pageSize"
                                   :total="totalPage"
                                   :current-page="currentPage"
                                   layout="prev,pager,next"
                                   @current-change="handleCurrentChange">
                    </el-pagination>
                </template>
                <!-- 修改信息的弹窗 -->
                <el-dialog :visible.sync="dialogVisible" title="修改账单信息" :destroy-on-close="true"
                           :befor-close="dialogClose" width="30%"
                           :lock-scroll="false" :close-on-click-modal="false" :close-on-press-escape="false">
                    <el-form :model="editForm" ref="editForm" :rules="rules">
                        <el-form-item label="日期" prop="date">
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="editForm.date" type="datetime"
                                            placeholder="选择日期" style="width: 100%;" :picker-options="today">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="类别" prop="bill_type">
                            <el-radio-group v-model="editForm.bill_type" @change="addBillTypeChange">
                                <el-radio label="收入" value="收入"></el-radio>
                                <el-radio label="支出" value="支出"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="种类" prop="kind">
                            <el-select v-model="editForm.kind">
                                <el-option v-for="category in categoryHandle"
                                           :key="category.category_name"
                                           :value="category.category_name">
                                    <span>{{ category.category_name }}</span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="金额" prop="amount">
                            <el-input v-model="editForm.amount"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" prop="info">
                            <el-input v-model="editForm.info"></el-input>
                            <el-input v-model="editForm.id" v-show="false" v-if="false"></el-input>
                        </el-form-item>
                    </el-form>
                    <div>
                        <el-button @click="dialogVisible=false">取消</el-button>
                        <el-button type="primary" @click="handleSave">保存</el-button>
                    </div>
                </el-dialog>

                <!-- 添加信息的弹窗 -->
                <el-dialog :visible.sync="addDialogVisible" title="添加账单信息" :destroy-on-close="true"
                           :befor-colse="addDialogClose" width="30%"
                           :close-on-click-modal="false" :close-on-press-escape="false">
                    <el-form :model="addForm" ref="addForm" :rules="rules">
                        <el-form-item>
                            <el-date-picker value-format="yyyy-MM-dd HH:mm:ss" v-model="addForm.date" type="datetime"
                                            placeholder="选择日期" style="width: 100%" :picker-options="today">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="类别" prop="bill_type">
                            <el-radio-group v-model="addForm.bill_type" @change="addBillTypeChange">
                                <el-radio label="收入" value="收入"></el-radio>
                                <el-radio label="支出" value="支出"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="种类" prop="kind">
                            <el-select v-model="addForm.kind">
                                <el-option v-for="category in categoryHandle"
                                           :key="category.category_name"
                                           :value="category.category_name">
                                    <span>{{ category.category_name }}</span>
                                    <span>{{ category.category_icon}}</span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="金额" prop="amount">
                            <el-input v-model="addForm.amount"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" prop="info">
                            <el-input v-model="addForm.info"></el-input>
                            <el-input v-model="addForm.id" v-show="false"></el-input>
                        </el-form-item>
                    </el-form>
                    <div>
                        <el-button @click="addDialogVisible=false">取消</el-button>
                        <el-button type="primary" @click="handleAddSave">保存</el-button>
                    </div>
                </el-dialog>
            </div>
        </el-main>
    </el-container>
</template>

<script>
    import {Message} from "element-ui";
    import dayjs from "dayjs";
    import fetchData from "@/plugins/tools";
    import * as incomeApi from "@/api/homeAPI/incomeApi";
    import * as expenditureApi from "@/api/homeAPI/expenditureApi";
    import * as EIApi from "@/api/homeAPI/expenditureIncomeApi"
    import * as categoryApi from "@/api/homeAPI/categoryApi"

    export default {
        name: "",
        data() {
            return {
                categoryHandle: [],
                tableDateTemplate: {
                    date: '',
                    bill_type: '',
                    kind: '',
                    amount: '',
                    notice: '',
                },
                addForm: '',
                addDialogVisible: false,
                totalPage: 0,
                pageSize: this.$store.state.pageSize,
                currentPage: 1,
                editId: '',
                editForm: '',
                dialogVisible: false,
                startTime: '',
                selectedName: '',//现在选择显示的时间单位
                datePickerFormat: '',
                datePickerPlaceholder: '选择周',
                tableData: [],//表格数据
                //今天
                today: {
                    shortcuts: [{
                        text: '现在',
                        onClick(picker) {
                            const date = new Date();
                            const year = date.getFullYear();
                            const month = (date.getMonth() + 1).toString().padStart(2, '0');
                            const day = date.getDate().toString().padStart(2, '0');
                            const hour = date.getHours().toString().padStart(2, '0');
                            const minute = date.getMinutes().toString().padStart(2, '0');
                            const second = date.getSeconds().toString().padStart(2, '0');

                            const formattedDate = `${year}-${month}-${day}`;
                            const formattedTime = `${hour}:${minute}:${second}`;

                            const dateTime = `${formattedDate}T${formattedTime}`;

                            picker.$emit('pick', dateTime);
                        }
                    }]
                },
                rules: {
                    date: [
                        {requested: true, type: 'date', message: '请填写日期', trigger: ['blur', 'submit']}
                    ],
                    bill_type: [
                        {required: true, message: '请填写账单类别', trigger: ['blur', 'submit']},
                        {
                            validator: (rule, value, callback) => {
                                if (value !== '收入' && value !== '支出') {
                                    callback(new Error('类别只能为收入或支出'));
                                } else {
                                    callback();
                                }
                            },
                            trigger: ['blur', 'submit']
                        }
                    ],
                    kind: [
                        {required: true, message: '请填写类型', trigger: ['blur', 'submit']}
                    ],
                    amount: [
                        {required: true, message: '请输入金额', trigger: ['blur', 'submit']},
                        //只能为整数或者浮点数
                        {pattern: /^\d+(\.\d+)?$/, message: '请输入数字', trigger: ['blur', 'submit']}
                    ]
                }
            }
        },
        methods: {
            addBillTypeChange() {
                this.addForm.kind = '';
                this.categoryHandle = this.$store.state.categoryList.filter(item => item.category_type === this.addForm.bill_type);
            },
            timeChanged() {
                this.doChange(this.currentPage - 1, this.pageSize);
            },
            handleAdd() {
                this.addDialogVisible = true;
                this.addForm = Object.assign({}, this.tableDateTemplate);
            },
            showIncomeDetails() {
                this.$store.state.tableSelected = 'incomeTable';
            },
            showExpenditureDetails() {
                this.$store.state.tableSelected = 'expenditureTable';
            },
            //处理页标改变
            handleCurrentChange(newPage) {
                this.currentPage = newPage;
                this.doChange(this.currentPage - 1, this.pageSize);
            },
            //获取周的收入支出数据
            fetchWeekData(apiName, page, size) {
                const weekStart = dayjs(this.$store.state.datePicker).startOf(this.$store.state.datePickerType).format('YYYY-MM-DD')
                const weekEnd = dayjs(this.$store.state.datePicker).endOf(this.$store.state.datePickerType).format('YYYY-MM-DD')
                fetchData(apiName, [weekStart, weekEnd, page, size], data => {
                    this.tableData = data.list
                    this.$store.state.totalExpenditure = data.expenditureTotal;
                    this.$store.state.totalIncome = data.incomeTotal;
                    this.totalPage = data.totalPage;
                })
            },
            //获取月的收入支出数据
            fetchMonthData(apiName, page, size) {
                const datePickerFormatted = dayjs(this.$store.state.datePicker).format('YYYY-MM');
                fetchData(apiName, [datePickerFormatted, page, size], data => {
                    this.tableData = data.list;
                    this.$store.state.totalExpenditure = data.expenditureTotal;
                    this.$store.state.totalIncome = data.incomeTotal;
                    this.totalPage = data.totalPage;

                })
            },
            //获取年的收入支出数据
            fetchYearData(apiName, page, size) {
                const datePickerFormatted = dayjs(this.$store.state.datePicker).format("YYYY");
                fetchData(apiName, [datePickerFormatted, page, size], data => {
                    this.tableData = data.list;
                    this.$store.state.totalExpenditure = data.expenditureTotal;
                    this.$store.state.totalIncome = data.incomeTotal;
                    this.totalPage = data.totalPage;
                })
            },
            dialogClose() {
                this.$confirm('确认关闭？').then(_ => {
                    done();
                }).catch(_ => {
                })
            },
            addDialogClose() {
                this.$confirm('确认关闭？').then(_ => {
                    done();
                }).catch(_ => {
                })
            },
            handleDelete(index, row) {
                this.$confirm("是否确认删除该账单记录？", "提示", {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //向后端同步数据
                    if (row.amount >= 0) {
                        //删除入账
                        incomeApi.detailIncomeIdDelete(row.id).then(res => {
                            Message.success("删除成功");
                            this.doChange(this.currentPage - 1, this.pageSize)
                            this.tableData.splice(index, 1);
                        })
                    } else {
                        //删除支出
                        expenditureApi.detailsOutIdDelete(row.id).then(res => {
                            Message.success("删除成功");
                            this.doChange(this.currentPage - 1, this.pageSize)
                            this.tableData.splice(index, 1);
                        })
                    }
                })
            },
            handleEdit(index, row) {
                this.editIndex = index;
                this.editForm = Object.assign({}, row);
                this.dialogVisible = true;
            },
            handleSave() {
                this.$confirm("请确认保存", "提示", {
                    cancelButtonText: '取消',
                    confirmButtonText: '确认'
                }).then(() => {
                    //向后端同步数据
                    if (this.editForm.bill_type === '收入') {
                        incomeApi.detailsIncomeIdPut(this.editForm).then(res => {
                            Message.success('修改成功');
                            Object.assign(this.tableData[this.editIndex], this.editForm);
                            this.dialogVisible = false;
                        })
                    }
                    if (this.editForm.bill_type === '支出') {
                        expenditureApi.detailsOutIdPut(this.editForm).then(res => {
                            Message.success('修改成功');
                            Object.assign(this.tableData[this.editIndex], this.editForm);
                            this.dialogVisible = false;
                        })
                    } else {
                        Message.warning("账单类别不正确")
                        return
                    }
                })
            },
            handleAddSave() {
                this.$confirm("请确认保存", "提示", {
                    cancelButtonText: '取消',
                    confirmButtonText: '确认'
                }).then(() => {
                    //addForm中规则校验
                    this.$refs.addForm.validate((valid) => {
                        if (valid) {
                            //向后端同步数据
                            if (this.addForm.bill_type === '收入') {
                                incomeApi.detailsIncome(this.addForm).then(res => {
                                    Message.success('添加成功')
                                    this.addForm.id = res.data.data;
                                    this.tableData.unshift(this.addForm);
                                    //如果tableData的长度大于pageSize，说明当前页已经满了,将数组最后一个元素删除
                                    if (this.tableData.length > this.pageSize) {
                                        this.tableData.pop();
                                    }
                                    // this.doChange(this.currentPage, this.pageSize)
                                    // 修改总收入
                                    this.$store.state.totalIncome += parseInt(this.addForm.amount);
                                    this.addDialogVisible = false;
                                }).catch(err => {
                                    Message.warning("错误信息：" + JSON.stringify(err));
                                })
                            } else if (this.addForm.bill_type === '支出') {
                                expenditureApi.detailsOut(this.addForm).then(res => {
                                    Message.success('添加成功')
                                    this.addForm.id = res.data.data;
                                    // this.doChange(this.currentPage, this.pageSize)
                                    this.tableData.unshift(this.addForm);
                                    //如果tableData的长度大于pageSize，说明当前页已经满了,将数组最后一个元素删除
                                    if (this.tableData.length > this.pageSize) {
                                        this.tableData.pop();
                                    }
                                    // 用state中的mutation修改总支出
                                    this.$store.state.totalExpenditure += parseInt(this.addForm.amount);
                                    this.addDialogVisible = false;

                                }).catch(err => {
                                    Message.warning("错误信息：" + JSON.stringify(err));
                                })
                            } else {
                                // Message.warning('未知类型')
                            }
                        } else {
                            Message.warning('请检查输入');
                        }
                    })
                })
            },
            //改变数据
            doChange(page, size) {
                this.tableData = null;
                switch (this.$store.state.datePickerType) {
                    case "week":
                        this.fetchWeekData('detailsStartEnd', page, size);
                        break;
                    case "month":
                        this.fetchMonthData('detailsMonthPageSize', page, size);
                        break;
                    case "year":
                        this.fetchYearData('detailsYearPageSize', page, size);
                        break;
                    default:
                        Message.error("未知 错误");
                }
            },
            //处理日期选择器的改变
            handleCommand(command) {
                this.$store.state.datePickerType = command;
                this.datePickerFormat = this.changeDatePickerFormat();
                this.datePickerPlaceholder = this.changeDatePickerPlaceholder();
                this.selectedName = this.changeSelectedName();
                this.currentPage = 1;
                this.doChange(this.currentPage - 1, this.pageSize);
            },
            //改变日期的提示
            changeSelectedName() {
                if (this.$store.state.datePickerType === 'week') {
                    return "每周";
                } else if (this.$store.state.datePickerType === 'month') {
                    return "每月";
                } else if (this.$store.state.datePickerType === 'year') {
                    return "每年";
                }
            },
            //改变日期的格式
            changeDatePickerFormat() {
                if (this.$store.state.datePickerType === 'week') {
                    return "yyyy 第 WW 周";
                } else if (this.$store.state.datePickerType === 'month') {
                    return "yyyy 第 MM 月";
                } else if (this.$store.state.datePickerType === 'year') {
                    return "yyyy 年"
                }
            },
            //改变日期提示框中的信息
            changeDatePickerPlaceholder() {
                if (this.$store.state.datePickerType === 'week') {
                    return '选择周'
                } else if (this.$store.state.datePickerType === 'month') {
                    return '选择月';
                } else if (this.$store.state.datePickerType === 'year') {
                    return '选择年';
                }
            },
        },
        created() {
            if (this.$store.state.datePickerType === null) {
                this.$store.commit('updateDatePickerType', this.$store.state.defPickerType);
            }
            this.handleCommand(this.$store.state.datePickerType);
            categoryApi.getAllCategory().then(res => {
                this.$store.state.categoryList = res.data.data.list;
            })
        },
    }
</script>

<style scoped>

</style>
