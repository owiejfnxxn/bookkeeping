<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <div>
            <el-row  style="background-color: rgba(255,255,255);margin-top: 10px">
                <!--周月年的选择-->
                <el-col :span="4" >
                    <el-dropdown @command="handleCommand" >
                        <el-button type="primary">
                            {{selected}}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu>
                            <el-dropdown-item command="每周">每周</el-dropdown-item>
                            <el-dropdown-item command="每月">每月</el-dropdown-item>
                            <el-dropdown-item command="每年">每年</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
                <!-- 日期的选择 -->
                <el-col :span="10">
                    <el-date-picker
                            v-model="datePicker"
                            :type="datePickerType"
                            :format="datePickerFormat"
                            :placeholder="datePickerPlaceholder">
                    </el-date-picker>
                </el-col>
                <!-- 统计值：收入 -->
                <el-col :span="3">
                    <div @click="showIncomeDetails">
                        <el-statistic :precision="2" :value="totalIncome" title="收入" ></el-statistic>
                    </div>

                </el-col>
                <!-- 统计值：支出 -->
                <el-col :span="3" @click="showExpenditureDetails">
                    <el-statistic :precision="2" :value="totalExpenditure" title="支出" ></el-statistic>
                </el-col>
                <!-- 添加新的账单记录 -->
                <el-col :span="4">
                    <el-button icon="el-icon-circle-plus-outline" style="font-size: 22px;">添加</el-button>
                </el-col>
            </el-row>
        </div>
        <el-main class="main">
    <div style="margin-top: 25px">
        <template>
            <el-table :data="tableData" ref="table" height="520" style="width:100%;">
                <el-table-column label="编号" prop="id"></el-table-column>
                <el-table-column label="日期" prop="date"></el-table-column>
                <el-table-column label="种类" prop="kind"></el-table-column>
                <el-table-column label="类别" prop="bill_type"></el-table-column>
                <el-table-column label="金额" prop="amount"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="备注" prop="info"></el-table-column>
            </el-table>
            <el-pagination :page-size="9"
                           :total="totalPage"
                           current-page="currentPage"
                           layout="prev,pager,next"
                           @current-change="handleCurrentChange">
            </el-pagination>
        </template>
        <el-dialog :visible.sync="dialogVisible" title="修改账单信息" :befor-close="dialogClose" width="30%">
            <el-form :model="editForm" ref="editForm">
                <el-form-item label="编号" prop="id">
                    <el-input v-model="editForm.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="日期" prop="date">
                    <el-input v-model="editForm.date"></el-input>
                </el-form-item>
                <el-form-item label="种类" prop="kind">
                    <el-input v-model="editForm.kind"></el-input>
                </el-form-item>
                <el-form-item label="金额" prop="amount">
                    <el-input v-model="editForm.amount" ></el-input>
                </el-form-item>
                <div>
                    <el-button @click="this.dialogVisible=false">取消</el-button>
                    <el-button type="primary" @click="handleSave">保存</el-button>
                </div>
            </el-form>
        </el-dialog>
    </div>
        </el-main>
    </el-container>
</template>

<script>
    import {Message} from "element-ui";
    import dayjs from "dayjs";
    import fetchData from "@/plugins/tools";
    import * as Income from "@/api/homeAPI/incomeApi";

    export default {
        name: "",
        data(){
            return{
                totalPage:'0',
                pageSize:'9',
                currentPage:'0',
                totalIncome:'0',
                totalExpenditure: '0',
                editId:'',
                editForm:'',
                dialogVisible:false,
                startTime:'',
                datePicker:new Date().getTime(),//dataPicker的module
                datePickerType:'week',
                selected:'每周',//现在选择显示的时间单位
                datePickerFormat:'yyyy 第 MM 周',
                datePickerPlaceholder:'选择周',
                tableData:[]//表格数据
            }
        },
        methods:{
            showIncomeDetails(){
                Message.info("等等，还没想好")
            },
            showExpenditureDetails(){
                Message.info("等等。还没想好")
            },
            //处理页标改变
            handleCurrentChange(newPage){
                this.currentPage = newPage;
                const start = (this.currentPage - 1)*this.pageSize;
                const end = start + this.pageSize;
                this.doChange(start,end);
            },
            //获取周的收入支出数据
            fetchWeekData(page,size){
                const weekStart = dayjs(this.datePicker).startOf(this.datePickerType).format('YYYY-MM-DD')
                const weekEnd = dayjs(this.datePicker).endOf(this.datePickerType).format('YYYY-MM-DD')
                fetchData('detailsStartEnd',[weekStart,weekEnd,page,size],data=>{
                    this.tableData = data.list
                    this.totalExpenditure = data.expenditureTotal;
                    this.totalIncome = data.incomeTotal;
                    this.totalPage = data.total;
                })
            },
            //获取月的收入支出数据
            fetchMonthData(page,size){
                const datePickerFormatted = dayjs(this.datePicker).format('YYYY-MM');
                fetchData('detailsMonthPageSize',[datePickerFormatted,page,size],data=>{
                    this.tableData = data.list;
                    this.totalExpenditure = data.expenditureTotal;
                    this.totalIncome = data.incomeTotal;
                    this.totalPage = data.total;

                })
            },
            fetchYearData(page,size){
                const datePickerFormatted = dayjs(this.datePicker).format("YYYY");
                fetchData('detailsYearPageSize',[datePickerFormatted,page,size],data =>{
                    this.tableData = data.list;
                    this.totalExpenditure = data.expenditureTotal;
                    this.totalIncome = data.incomeTotal;
                    this.totalPage = data.total;
                    Message.info('totalPage:'+ data.total);
                })
            },
            //获取年的收入支出数据
            dialogClose(){
                this.$confirm('确认关闭？').then(_ =>{
                    done();
                }).catch(_ =>{
                })
            },
            handleDelete(index,row){
                this.$confirm("是否确认删除该账单记录？","提示",{
                    confirmButtonText: '确定',
                    cancelButtonText:'取消',
                    type:'warning'
                }).then(()=>{
                    //向后端同步数据
                    if(row.amount >= 0){
                        //删除入账
                        Income.detailIncomeIdDelete(row.id).then(res =>{
                            Message.success("删除成功");
                            this.tableData.splice(index,1);
                        })
                    }else{
                        //删除支出
                        Income.detailIncomeIdDelete(row.id).then(res =>{
                            Message.success("删除成功");
                            this.tableData.splice(index,1);
                        })
                    }
                })

            },
            handleEdit(index,row){
                this.editIndex = index;
                this.editForm = Object.assign({},row);
                this.dialogVisible = true;
            },
            handleSave(){
                this.$confirm("请确认保存","提示",{
                    cancelButtonText:'取消',
                    confirmButtonText:'确认'
                }).then(() =>{
                    //向后端同步数据
                    // if()
                    Object.assign(this.tableData[this.editIndex],this.editForm);
                })

                this.dialogVisible = false;
            },
            //改变数据
            doChange(page,size){
                switch (this.datePickerType){
                    case "week": this.fetchWeekData(page,size);break;
                    case "month": this.fetchMonthData(page,size);break;
                    case "year": this.fetchYearData(page,size);break;
                    default:Message.error("未知 错误");
                }
            },
            //处理日期选择器的改变
            handleCommand(command){
                this.selected = command;
                this.datePickerType=this.changeDatePickerType();
                this.datePickerFormat=this.changeDatePickerFormat();
                this.datePickerPlaceholder=this.changeDatePickerPlaceholder();
                this.doChange(this.currentPage,this.pageSize);
            },
            //改变日期选择器的类型
            changeDatePickerType(){
                if(this.selected ==='每周'){
                    return "week";
                }else if(this.selected === '每月'){
                    return "month";
                }else if(this.selected === '每年'){
                    return "year";
                }
            },
            //改变日期的格式
            changeDatePickerFormat(){
                if(this.selected ==='每周'){
                    return "yyyy 第 WW 周";
                }else if(this.selected === '每月'){
                    return "yyyy 第 MM 月";
                }else if(this.selected === '每年'){
                    return "yyyy 年"
                }
            },
            //改变日期提示框中的信息
            changeDatePickerPlaceholder(){
                if(this.selected === '每周'){
                    return '选择周'
                }else if(this.selected === '每月'){
                    return '选择月';
                }else if(this.selected === '每年'){
                    return '选择年';
                }
            },
        },
        created() {
            this.fetchWeekData(this.currentPage,this.pageSize);
        }

    }
</script>

<style scoped>

</style>
