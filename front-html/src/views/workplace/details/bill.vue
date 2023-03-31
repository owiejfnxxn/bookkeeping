<template>
    <el-container direction="vertical" style="height: auto;width: auto">
        <el-main class="main">
            <component :is="currentTable"></component>
        </el-main>
    </el-container>
</template>

<script>
    import EITable from "@/views/workplace/details/bill_table/EITable";
    import incomeTable from "@/views/workplace/details/bill_table/incomeTable";
    import expenditureTable from "@/views/workplace/details/bill_table/expenditureTable";
    export default {
        name: "",
        data() {
            return {
            }
        },
        computed: {
            currentTable() {
                return this.$store.state.tableSelected;
            },
        },
        components: {
            'EITable': EITable,
            'incomeTable': incomeTable,
            'expenditureTable': expenditureTable
        }
    }
</script>

<style lang="less" scoped>
  .total {
    display: inline;
    max-height: 50px;
  }

  .header {
    background-color: #b3d4fc;
    height: 100px;
  }

  .main {
    background-color: white;
    min-height: 600px;
  }

  .footer {
    background-color: blueviolet;
  }
</style>
