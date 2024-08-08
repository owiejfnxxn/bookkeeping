<template>
    <div @father="doFather">
        父组件
        <div>
            <el-button @click="doSon">子组件</el-button>
        </div>
    </div>
</template>

<script>
    import {Message} from "element-ui";

    export default {
        name: "",
        data() {
            return {
            }
        },
        methods: {
            doFather() {
                Message.info("我是父组件的方法")
            },
            doSon(){
                this.$emit("father");
                console.log("我是子组件的方法")
            }
        },
    }
</script>

<style scoped>

</style>
