<template>
    <el-container direction="vertical" style="width: auto;height: auto">
        <el-main style="background-color: white;min-height: 600px;">
            <el-statistic group-separator="," :precision="2" :value="$store.state.totalIncome" title="总收入"></el-statistic>
            <div>
                <div style="margin-top: 25px;width: 100%;float: left;height: 100%;">
                    <el-switch v-model="selected" active-color="#87CEFA" inactive-color="#87CEFA" active-text="按金额"
                               inactive-text="按日期" @change="getTableDate">
                    </el-switch>
                    <el-table :data="tableData" ref="table" height="520" style="width:100%;">
                        <el-table-column label="编号" type="index" align="center"></el-table-column>
                        <el-table-column label="日期" prop="date" align="center"></el-table-column>
                        <el-table-column label="种类" prop="kind" align="center"></el-table-column>
                        <el-table-column label="金额" prop="amount" align="center"></el-table-column>
                    </el-table>
                    <el-pagination :page-size="9"
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
    import * as incomeApi from '@/api/homeAPI/incomeApi'
    import dayjs from "dayjs";
    import {createChart} from "@/plugins/tools";

    export default {
        name: "",
        data() {
            return {
                selected: false,
                totalPage:0,
                currentPage: 0,
                size: 9,
                tableData: [],
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
                    incomeApi.detailsTimeFrameIncome(start_time, end_time, 'amount', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.total;
                        })
                } else {
                    // 按日期
                    let start_time = dayjs(this.$store.state.datePicker).startOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    let end_time = dayjs(this.$store.state.datePicker).endOf(this.$store.state.datePickerType).format('YYYY-MM-DD');
                    incomeApi.detailsTimeFrameIncome(start_time, end_time, 'date', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data().list;
                            this.totalPage = res.data.data.total;
                        })
                }
            },
            monthDate() {
                if (this.selected) {
                    let monthFormatted = dayjs(this.$store.state.datePicker).format('YYYY-MM');
                    incomeApi.detailsMonthIncome(monthFormatted, 'amount', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.total;
                        })
                } else {
                    let monthFormatted = dayjs(this.$store.state.datePicker).format('YYYY-MM');
                    incomeApi.detailsMonthIncome(monthFormatted, 'date', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.total;
                        })
                }
            },
            yearDate() {
                if (this.selected) {
                    let yearFormatted = dayjs(this.$store.state.datePicker).format('YYYY');
                    incomeApi.detailsYearIncome(yearFormatted, 'amount', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.total;
                        })
                } else {
                    let yearFormatted = dayjs(this.$store.state.datePicker).format('YYYY');
                    incomeApi.detailsMonthIncome(yearFormatted, 'date', this.currentPage, this.size)
                        .then(res => {
                            this.tableData = res.data.data.list;
                            this.totalPage = res.data.data.total;
                        })
                }
            },
            handleCurrentChange(newPage){
                this.currentPage = newPage;
                const start = (this.currentPage - 1) * this.pageSize;
                const end = start + this.pageSize;
                this.doChange(start, end);
            }
        },
        created() {
            Message.info(this.$store.state.datePickerType + '   ' + this.$store.state.datePicker)
            this.getTableDate();
        },
    }
</script>

<style scoped>

</style>
