<template>
    <div>

        <el-container class="index">
            <el-header class="header">记账本</el-header>
            <el-container class="main" direction="horizontal">
                <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                <el-menu :default-openeds="['1', '3']">
                    <el-submenu index="1">
                        <template slot="title"><i class="el-icon-message"></i>明细</template>
                            <el-menu-item index="1-1" @click="showBill">账单</el-menu-item>
                            <el-menu-item index="1-2" @click="showBudget">预算</el-menu-item>
                            <el-menu-item index="1-3">没想好</el-menu-item>
                    </el-submenu>
                    <el-submenu index="2">
                        <template slot="title"><i class="el-icon-pie-chart"></i>图表</template>
                            <el-menu-item index="2-1">周</el-menu-item>
                            <el-menu-item index="2-2">月</el-menu-item>
                            <el-menu-item index="2-3">年</el-menu-item>
                    </el-submenu>
                    <el-submenu index="3">
                        <template slot="title"><i class="el-icon-user"></i>我的</template>
                        <el-menu-item-group>
                            <template slot="title">分组一</template>
                            <el-menu-item index="3-1">选项1</el-menu-item>
                            <el-menu-item index="3-2">选项2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分组2">
                            <el-menu-item index="3-3">选项3</el-menu-item>
                        </el-menu-item-group>
                        <el-submenu index="3-4">
                            <template slot="title">选项4</template>
                            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
                        </el-submenu>
                    </el-submenu>
                </el-menu>
            </el-aside>
                <el-main>
                    <component :is="currentComponent"></component>
                </el-main>
            </el-container>
            <el-footer class="footer">footer</el-footer>
        </el-container>
    </div>

</template>

<script>
    import bill from './workplace/details/bill';
    import budget from "@/views/workplace/details/budget";

    export default {
        name: "",
        data(){
            return{
                now:'bill'
            }
        },
        methods:{
            showBill(){
                this.now='bill';
            },
            showBudget(){
                this.now='budget'
            }
        },
        components:{
            'bill' : bill,
            'budget' : budget,
        },
        computed:{
            currentComponent(){
                return this.now;
            }
        }
    }
</script>

<style lang="less" scoped>
    .index{
    }
    .header{
      font-size: 40px;
      background-color: skyblue;
      text-align: right;
    }
    .main{
      min-height: 600px;
    }
    .header{
      background-color: skyblue;
    }
    .footer{
      background-color: #b3d4fc;
    }
</style>
