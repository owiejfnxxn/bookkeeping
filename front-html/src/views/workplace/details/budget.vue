<template>
    <div>
        <div class="grid-container" ref="topItem" @click="totalBudgetEditHandle">
            <div style="margin-left: 25px"> {{ title }}</div>
            <div style="text-align: right;margin-right: 25px">编辑</div>
            <div class="grid-item">
                <div style="display: flex;justify-content: space-between;background-color: white">
                    <!-- 显示进度条 -->
                    <div style="flex: 1">
                        <el-progress type="circle" :percentage="totalBudgetPercentage(usedBudget,totalBudget)"
                                     define-back-color="#DCDCDC" stroke-linecap="butt"
                                     class="countdown-progress" style="margin-left: 25px" :format="elProgressFormat"
                                     :color="colors"></el-progress>
                    </div>
                    <!-- 显示预算值 -->
                    <div style="flex: 1;">
                        <div style="display: flex;">
                            <div class="RemainingBudget">剩余预算：</div>
                            <div class="RemainingBudget" style="text-align: right">{{ totalBudget - usedBudget }}</div>
                        </div>
                        <hr>
                        <div style="display: flex;margin-top: 10px">
                            <div class="commonBudget">总预算：</div>
                            <div class="commonBudget" style="text-align: right">{{ totalBudget }}</div>
                        </div>
                        <div style="display: flex;margin-top: 10px">
                            <div class="commonBudget">总支出：</div>
                            <div class="commonBudget" style="text-align: right">{{ usedBudget }}</div>
                        </div>
                    </div>

                    <!-- 显示预算的时间 -->
                    <div style="flex: 3;margin-right: 25px">
                        <p style="text-align: right">
                            <!-- el-dropdown的下拉框，可以选着年或月,能动态显示 -->
                            <el-dropdown @command="handleCommand">
                                <span class="el-dropdown-link" style="background-color: #b3d4fc">
                                    {{ selected === 'month' ? '月预算' : '年预算' }}<i
                                        class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="month">月预算</el-dropdown-item>
                                    <el-dropdown-item command="year">年预算</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <!-- 类别预算 -->
        <div style="background-color: black;width: 100%;margin-top: 10px">
            <el-table :data="tableData" height="520">
                <el-table-column>
                    <template v-slot:header>
                        <div style="display: flex;justify-content: space-between">
                            <div style="flex: 1">类别预算</div>
                            <el-button size="small" @click="addKindBudgetHandle">添加预算类别</el-button>
                        </div>
                    </template>
                    <template v-slot="scope">
                        <div>
                            <div>
                                <div style="display: inline;">
                                    {{ scope.row.name }}
                                </div>
                                <div style="display: inline;margin-left: 1000px;text-align: right;">
                                    <template>
                                        <el-button type="text" icon="el-icon-delete" circle
                                                   @click="kindDeleteHandle(scope.$index,scope.row)">删除
                                        </el-button>
                                        <el-button type="text" icon="el-icon-edit" circle
                                                   @click="kindEditHandle(scope.$index,scope.row)">编辑
                                        </el-button>
                                    </template>
                                </div>
                            </div>
                            <div>
                                <el-progress
                                        :percentage="totalBudgetPercentage(scope.row.usedBudget,scope.row.totalBudget)"
                                        :status="scope.row.status" :color="colors"></el-progress>
                            </div>
                            <div>
                                剩余的 {{ scope.row.totalBudget - scope.row.usedBudget }}
                            </div>
                            <div>
                                已使用的 {{ scope.row.usedBudget }}
                            </div>
                            <div>
                                计划的 {{ scope.row.totalBudget }}
                            </div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 一个对话框,用来对总预算值进行清空 -->
        <el-dialog :title=this.editTitle :visible.sync="dialogVisible" @close="editDialogClose">
            <span>{{ editInputTitle }}</span>
            <el-input placeholder="请输入内容" v-model="editBudgetTotal"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogClose;dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editDialogConfirm">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 一个对话框,用来添加(支出)类别预算 -->
        <el-dialog title="添加类别预算" :visible.sync="addDialogVisible">
            <span>类别名称</span>
            <el-select v-model="addKindName">
                <el-option
                        v-for="item in categoryList"
                        :key="item.category_name"
                        :label="item.category_name"
                        :value="item.category_name">
                    <span>{{ item.category_name }}</span>
                </el-option>
            </el-select>
            <br>
            <span>类别预算</span>
            <el-input placeholder="请输入内容" v-model="addKindBudget"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogClose;addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addDialogConfirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import * as budgetApi from '@/api/homeAPI/budgetApi'
    import {Message} from "element-ui";
    import dayjs from "dayjs";

    export default {
        name: "",
        data() {
            return {
                //标题
                title: '',
                //表格数据
                tableData: [],
                //日期类型的选着值
                selected: 'month',
                //编辑总预算的对话框
                dialogVisible: false,
                //编辑的值
                editBudgetTotal: 0,
                //总预算的值
                totalBudget: '',
                //剩余预算的值
                restBudget: '',
                //已使用的预算的值
                usedBudget: '',
                //总预算使用的百分比
                percentage: 0,
                //总预算的状态
                colors: [
                    {color: '#f56c6c', percentage: 90},
                    {color: '#e6a23c', percentage: 70},
                    {color: '#5cb87a', percentage: 50},
                    {color: '#1989fa', percentage: 30},
                    {color: '#6f7ad3', percentage: 0}
                ],
                //编辑框的标题
                editTitle: '',
                //编辑框的输入框的标题
                editInputTitle: '',
                //添加类别预算的对话框
                addDialogVisible: false,
                //添加类别预算的名称
                addKindName: '',
                //添加类别预算的预算
                addKindBudget: '',
                //类别预算的列表
                categoryList: [],
            }
        },
        methods: {
            //类别预算的编辑
            kindEditHandle(index, row) {
                this.editTitle = row.name;
                this.editInputTitle = row.name + "预算";
                this.editBudgetTotal = row.totalBudget;
                //打开编辑框
                this.dialogVisible = true;
            },
            //总预算的编辑
            totalBudgetEditHandle() {
                this.editTitle = '总预算';
                this.editInputTitle = '总预算';
                this.editBudgetTotal = this.totalBudget;
                this.dialogVisible = true;
            },
            //日期类型的选着值
            handleCommand(command) {
                //清除数据
                this.tableData = [];
                this.totalBudget = 0;
                this.usedBudget = 0;
                this.selected = command;
                //向后端请求更新数据
                budgetApi.getBudgetInfo(command, command === 'month' ? dayjs().format("YYYY-MM") : dayjs().format("YYYY")).then(res => {
                    let data = res.data.data;
                    this.tableData = data.tableData;
                    this.title = data.title;
                    this.totalBudget = data.totalBudget;
                    this.usedBudget = data.usedBudget;
                    this.percentage = parseInt(data.usedBudget * 1.0 / data.totalBudget * 100);
                }).catch(err => {
                    Message.warning("获取数据失败");
                    console.log(err);
                })
            },
            //百分比的格式化
            elProgressFormat(percentage) {
                if (percentage > 100)
                    return "已超出:" + percentage + "%"
                else
                    return "已使用:" + percentage + "%"
            },
            // 清空编辑框的状态
            editDialogClose() {
                this.editInputTitle = '';
                this.editBudgetTotal = 0;
                this.editTitle = '';
            },
            // 编辑框的确定按钮
            editDialogConfirm() {
                //更新数据
                budgetApi.updateBudgetInfo(
                    this.selected,
                    this.selected === 'month' ? dayjs().format("YYYY-MM") : dayjs().format('YYYY'),
                    this.editTitle,
                    this.editBudgetTotal)
                    .then(res => {
                        this.editTitle === '总预算' ? this.totalBudget = this.editBudgetTotal : this.tableData.find(item => item.name === this.editTitle).totalBudget = this.editBudgetTotal;
                        Message.info('更新成功');
                        //清空编辑框的状态
                        this.editDialogClose();
                        //关闭编辑框
                        this.dialogVisible = false;
                    })
            },
            // 计算总预算的百分比
            totalBudgetPercentage(used, total) {
                if (total === 0 || total === undefined || total === null)
                    return 0;
                return parseInt((used / total * 100));
            },
            // 添加类别预算的按钮
            addKindBudgetHandle() {
                this.addDialogVisible = true;
                // 从store中获取类别表
                this.kindName();
            },
            // 添加类别预算的确定按钮
            addDialogConfirm() {
                budgetApi.setKindBudgetValue(this.selected,
                    this.selected === 'month' ? dayjs().format("YYYY-MM") : dayjs().format('YYYY'),
                    this.addKindName,
                    this.addKindBudget).then(res => {
                    //更新数据
                    this.tableData.push({
                        name: this.addKindName,
                        usedBudget: res.data.data,
                        totalBudget: this.addKindBudget,
                    });
                    Message.success('添加成功');
                    //销毁数据
                    this.addDialogClose();
                    //关闭编辑框
                    this.addDialogVisible = false;
                })


            },
            // 清空添加类别预算的状态
            addDialogClose() {
                this.addKindName = '';
                this.addKindBudget = '';
            },
            // 获取类别
            kindName() {
                this.categoryList = this.$store.state.categoryList.filter(category => category.category_type === "支出");
            },
            // 删除类别预算的按钮
            kindDeleteHandle(index, row) {
                this.$confirm('此操作将永久删除该类别预算, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    budgetApi.deleteBudgetInfo(this.selected === 'month' ? dayjs().format("YYYY-MM") : dayjs().format('YYYY'),
                        row.name).then(res => {
                    })
                    //删除数据
                    Message.success("删除成功");
                    this.tableData.splice(index, 1);
                }).catch(() => {
                    Message.info('已取消删除');
                });

            }
        },
        created() {
            // 从后端获取数据
            budgetApi.getBudgetInfo(this.selected, this.selected === 'month' ? dayjs().format("YYYY-MM") : dayjs().format('YYYY'))
                .then(res => {
                    let data = res.data.data;
                    this.tableData = data.tableData;
                    this.title = data.title;
                    this.totalBudget = data.totalBudget;
                    this.usedBudget = data.usedBudget;
                    this.percentage = parseInt(data.usedBudget * 1.0 / data.totalBudget * 100);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }
</script>

<style scoped>
    .RemainingBudget {
        flex-grow: 1;
        flex-shrink: 1;
        flex-basis: 0;
        font-size: 20px;
        font-weight: 500
    }

    .commonBudget {
        flex-grow: 1;
        flex-shrink: 1;
        flex-basis: 0;
    }

    .grid-container {
        background-color: white;
        display: grid;
        grid-template-rows: 2fr 9fr;
        grid-template-columns: repeat(2, 1fr);
    }

    .grid-item {
        grid-row: 2 /span 0;
        grid-column: 1 /span 2;
    }
</style>
