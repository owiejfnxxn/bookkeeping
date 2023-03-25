<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <el-header class="header">
            <el-date-picker style="top: 15%"></el-date-picker>
            <div class="billInfo">
                <div class="revenue">
                    <div>收入</div>
                    <div>123</div>
                </div>
                <div class="expenditures">
                    <div>支出</div>
                    <div>123</div>
                </div>
            </div>
        </el-header>
        <el-main class="main">
            main
        </el-main>
        <el-footer class="footer">
            footer
        </el-footer>
    </el-container>
</template>

<script>

    export default {
        name: "",
        methods:{
            testJs(){
            }
        }
    }
</script>

<style lang="less" scoped>
.billInfo{
  position: absolute;
  right: 40px;
  top:90px;
  .expenditures{
    width: fit-content;
    display: inline;
  }
  .revenue{
    width: fit-content;
    display: inline;
  }
}
  .header{
  background-color: #b3d4fc;
}
.main{
  background-color: white;
}
.footer{
  background-color: blueviolet;
}
</style>
