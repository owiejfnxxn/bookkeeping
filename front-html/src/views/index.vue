<template>
    <div>
        <el-container class="index">
            <el-header class="header">
                <span style="margin-right: 600px">记账本</span>
                <font-awesome-icon :icon="['fas', 'hand-holding-dollar']" bounce size="xl" style="color: #7cf4c0;"
                                   @click="iconClick"/>
            </el-header>
            <el-container class="main" direction="horizontal">
                <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                    <el-menu :default-openeds="['1','2','3']">

                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>明细</template>
                            <el-menu-item index="1-1" @click="showBill">账单</el-menu-item>
                            <el-menu-item index="1-2" @click="showIncomeTable">收入</el-menu-item>
                            <el-menu-item index="1-3" @click="showExpenditureTable">支出</el-menu-item>
                            <el-menu-item index="1-4" @click="showBudget">预算</el-menu-item>
                        </el-submenu>

                        <el-submenu index="2">
                            <template slot="title"><i class="el-icon-pie-chart"></i>图表</template>
                            <el-menu-item index="2-1" @click="showWeekChart">周</el-menu-item>
                            <el-menu-item index="2-2" @click="showMonthChart">月</el-menu-item>
                            <el-menu-item index="2-3" @click="showYearChart">年</el-menu-item>
                        </el-submenu>

                        <el-submenu index="3">
                            <template slot="title"><i class="el-icon-user"></i>我的</template>
                            <!--                            <el-menu-item-group>-->
                            <!--                                <template slot="title">个人信息</template>-->
                            <!--                            </el-menu-item-group>-->
                            <!--                            <el-menu-item-group title="设置">-->
                            <el-menu-item index="3-3" @click="showUserSetting">通用设置</el-menu-item>
                            <!--                            </el-menu-item-group>-->
                        </el-submenu>

                    </el-menu>
                </el-aside>
                <el-main>
                    <component :is="currentComponent"></component>
                </el-main>
            </el-container>

            <el-footer class="footer" style="text-align: center">
                <font-awesome-icon :icon="['fas', 'laptop-code']" size="2xl" style="color: #2d508f;"/>
                <br/>
                王志宏、蔡启瑞、唐文君、杨丰玮
            </el-footer>
        </el-container>
        <el-dialog :visible.sync="bug" :close-on-press-escape="false" :close-on-click-modal="false" style="height: auto;width: auto">
            <el-statistic :value="staValue" time-indices title="电脑即将爆炸" @finish="egg"></el-statistic>
        </el-dialog>
    </div>

</template>

<script>
    import bill from './workplace/details/bill';
    import budget from "@/views/workplace/details/budget";
    import expenditureTable from "@/views/workplace/details/bill_table/expenditureTable";
    import incomeTable from "@/views/workplace/details/bill_table/incomeTable";
    import userSetting from "@/views/workplace/user/userInfo/userSetting";
    import monthChart from "@/views/workplace/chart/monthChart";
    import weekChart from "@/views/workplace/chart/weekChart";
    import yearChart from "@/views/workplace/chart/yearChart";

    export default {
        name: "",
        data() {
            return {
                now: 'bill',
                clickNums: 0,
                bug: false,
                staValue: 0,
            }
        },
        methods: {
            showBill() {
                this.now = 'bill';
            },
            showBudget() {
                this.now = 'budget'
            },
            showIncomeTable() {
                this.now = 'incomeTable'
            },
            showExpenditureTable() {
                this.now = 'expenditureTable'
            },
            showUserSetting() {
                this.now = 'userSetting'
            },
            showMonthChart() {
                this.now = 'monthChart'
            },
            showWeekChart() {
                this.now = 'weekChart'
            },
            showYearChart() {
                this.now = 'yearChart'
            },
            iconClick() {
                this.clickNums++;
                if (this.clickNums === 5) {
                    this.$message({
                        message: '你已经点了五下了，不要点了',
                        type: 'warning'
                    });
                }
                if (this.clickNums === 10) {
                    this.$message({
                        message: '你已经点了十下了，在点电脑关机',
                        type: 'error'
                    });
                }
                if (this.clickNums === 15) {
                    this.bug = true;
                    this.staValue = Date.now() + 10 * 1000
                    this.clickNums = 0;
                }
            },
            egg() {
                this.$message({
                    message: '请节约用钱',
                    type: 'success'
                });
                this.bug = false;
            }
        },
        components: {
            'bill': bill,
            'budget': budget,
            'expenditureTable': expenditureTable,
            'incomeTable': incomeTable,
            'userSetting': userSetting,
            'monthChart': monthChart,
            'weekChart': weekChart,
            'yearChart': yearChart
        },
        computed: {
            currentComponent() {
                return this.now;
            }
        }
    }
</script>

<style lang="less" scoped>
  .index {
  }

  .header {
    font-size: 40px;
    background-color: rgb(255, 160, 122);
    text-align: right;
  }

  .main {
    min-height: 600px;
  }

  .footer {
    background-color: #b3d4fc;
  }
</style>
