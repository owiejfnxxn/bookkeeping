<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <el-main class="main">

<!--                <el-statistic title="收入" :value="1234"></el-statistic>-->
<!--                <el-statistic title="支出" :value="123"></el-statistic>-->
            <div>
                <el-row style="background-color: wheat" gutter="5">
                    <el-col :span="8">
                        <el-date-picker
                                v-model="week"
                                type="week"
                                format="yyyy 第 WW 周"
                                placeholder="选择周">
                        </el-date-picker>
                    </el-col>
                    <el-col :span="4"></el-col>
                    <el-col :span="4" offset="4">
                            <el-statistic group-separator="," :precision="2" :value="123" title="收入"></el-statistic>
                    </el-col>
                    <el-col :span="8">
                        <el-statistic :precision="2" :value="1234" title="支出"></el-statistic>
                    </el-col>
                </el-row>
                </div>
        </el-main>
    </el-container>
</template>

<script>

    export default {
        name: "",
        data(){
            return{
                week:''
            }
        },
        methods:{

        },
    }
</script>

<style lang="less" scoped>
.total{
  display: inline;
  max-height: 50px;
}
  .header{
  background-color: #b3d4fc;
    height: 100px;
}
.main{
  background-color: white;
  min-height: 600px;
}
.footer{
  background-color: blueviolet;
}
</style>
