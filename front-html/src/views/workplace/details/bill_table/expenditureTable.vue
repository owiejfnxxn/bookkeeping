<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <el-main class="main">
            <div>
                <el-statistic group-separator="," :precision="2" :value="$store.state.totalExpenditure" title="总支出"></el-statistic>
                <div style="margin-top: 25px">
                    <el-switch v-model="selected" active-color="#87CEFA" inactive-color="#87CEFA" active-text="按金额"
                               inactive-text="按日期" @change="getTableDate">
                    </el-switch>
                    <el-table :data="tableData" ref="table" height="520" style="width:100%;">
                        <el-table-column label="编号" type="index" align="center">
                            <template slot-scope="scope">
                                <span>{{scope.$index + (currentPage - 1) * pageSize + 1}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="日期" prop="date" align="center"></el-table-column>
                        <el-table-column label="种类" prop="kind" align="center"></el-table-column>
                        <el-table-column label="金额" prop="amount" align="center"></el-table-column>
                    </el-table>
                    <el-pagination :page-size="pageSize"
                                   :total="totalPage"
                                   :current-page="currentPage"
                                   layout="prev,pager,next"
                                   @current-change="handleCurrentChange"
                    >
                    </el-pagination>
                </div>
            </div>
        </el-main>
    </el-container>

</template>

<script>
    import {Message} from "element-ui";
    import * as expenditureApi from '@/api/homeAPI/expenditureApi'
    import dayjs from "dayjs";

    export default {
        name: "",
        data() {
            return {
                selected: false,
                totalPage:0,
                currentPage: 1,
                tableData: [],
                pageSize: this.$store.state.pageSize,
            }
        },
        methods: {
            goBack() {
                this.$store.state.tableSelected = 'EITable';
            },
            getTableDate() {
                switch (this.$store.state.datePickerType) {
                    case "week":
                        this.weekDate();
                        break;
                    case "month":
                        this.monthDate();
                        break;
                    case "year":
                        this.yearDate();
                        break;
                    default:
                        Message.error('日期类型不正确');
                        break;
                }
            },
            weekDate() {
                if (this.selected) {
                    // 按金额
                    let start_time = dayjs(this.$store.state.datePicker).startOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    let end_time = dayjs(this.$store.state.datePicker).endOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    expenditureApi.detailsExpenditureTimeFrame(start_time, end_time, 'amount', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                } else {
                    // 按日期
                    let start_time = dayjs(this.$store.state.datePicker).startOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    let end_time = dayjs(this.$store.state.datePicker).endOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    expenditureApi.detailsExpenditureTimeFrame(start_time, end_time, 'date', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                }
            },
            monthDate() {
                if (this.selected) {
                    let monthFormatted = dayjs(this.$store.state.datePicker).format('YYYY-MM');
                    expenditureApi.detailsExpenditureMonth(monthFormatted, 'amount', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                } else {
                    let monthFormatted = dayjs(this.$store.state.datePicker).format('YYYY-MM');
                    expenditureApi.detailsExpenditureMonth(monthFormatted, 'date', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                }
            },
            yearDate() {
                if (this.selected) {
                    let yearFormatted = dayjs(this.$store.state.datePicker).format('YYYY');
                    expenditureApi.detailsExpenditureYear(yearFormatted, 'amount', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                } else {
                    let yearFormatted = dayjs(this.$store.state.datePicker).format('YYYY');
                    expenditureApi.detailsExpenditureYear(yearFormatted, 'date', this.currentPage-1, this.pageSize)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.totalPage;
                        })
                }
            },
            handleCurrentChange(newPage){
                this.currentPage = newPage;
                const start = (this.currentPage - 1) * this.pageSize;
                const end = start + this.pageSize;
                this.getTableDate();
                // this.doChange(this.currentPage - 1, this.pageSize);
            }
        },
        created() {
            this.getTableDate();
        }
    }
</script>

<style scoped>
    .main {
        background-color: white;
        min-height: 600px;
    }
</style>
