<template>
    <div>
        <div class="grid-container" ref="topItem" @click="totalBudgetEditHandle" >
            <div style="margin-left: 25px">04月总预算</div>
            <div style="text-align: right;margin-right: 25px">编辑</div>
            <div class="grid-item">
                <div style="display: flex;justify-content: space-between;background-color: white">
                    <div style="flex: 1">
                        <el-progress type="circle" :percentage="20" define-back-color="#FFD700" stroke-linecap="butt"
                                     class="countdown-progress" style="margin-left: 25px"></el-progress>
                    </div>
                    <div style="flex: 1;">
                        <div style="display: flex;">
                            <div class="RemainingBudget">左边的文字</div>
                            <div class="RemainingBudget" style="text-align: right">右边的文字</div>
                        </div>
                        <hr>
                        <div style="display: flex;margin-top: 10px">
                            <div class="commonBudget">左边的文字</div>
                            <div class="commonBudget" style="text-align: right">右边的文字</div>
                        </div>
                        <div style="display: flex;margin-top: 10px">
                            <div class="commonBudget">左边的文字</div>
                            <div class="commonBudget" style="text-align: right">右边的文字</div>
                        </div>
                    </div>
                    <div style="flex: 3;margin-right: 25px">
                        <p style="text-align: right">
                            <el-button @click="buttonTest" @click.native.stop>一个按钮摆了</el-button>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div style="background-color: black;width: 100%;margin-top: 10px">
            <el-table :data="tableData" height="520">
                <el-table-column label="类别预算">
                    <template v-slot="scope" >
                        <div @click="kindEditHandle(scope.$index,scope.row)">
                            <div>
                                <div style="display: inline;">
                                    类别名称
                                </div>
                                <div style="display: inline;margin-left: 1100px;text-align: right;">
                                    编辑
                                </div>
                            </div>
                            <div>
                                <el-progress :percentage="scope.row.progress" :status="scope.row.status"></el-progress>
                            </div>
                            <div>
                                剩余的
                            </div>
                            <div>
                                已使用的
                            </div>
                            <div>
                                计划的
                            </div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>


</template>

<script>
    //copilot:enable
    export default {
        name: "",
        data() {
            return {
                tableData: [
                    {
                        progress: 38
                    },
                    {
                        progress: 40
                    },
                    {
                        progress: 50
                    },
                    {
                        progress: 60
                    }
                ]
            }
        },
        methods: {
            kindEditHandle(index,row){
                console.log(row.progress);
            },
            totalBudgetEditHandle(){
                console.log(123)
            },
            buttonTest(){
                console.log(234);
            }
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
