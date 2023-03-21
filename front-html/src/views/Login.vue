<template>
    <div id="login-box">
        <!-- 头像区域 -->
        <div class="avatar_box">
            <img src="../assets/logo.png" alt="">
        </div>

        <!-- 登录表单区域 -->
        <div >
            <el-form ref="loginFormRef"  label-width="0px" class="login_form" :model="loginForm" :rules="loginRules">
                <!-- 用户名区域 -->
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="el-icon-user-solid" prop></el-input>
                </el-form-item>
                <!-- 密码区域 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="iconfont icon-mima" show-password></el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                    <!-- 登录按钮 -->
                    <el-button type="primary" @click="userLogin">登录</el-button>
                    <!-- 注册按钮 -->
                    <el-button type="info" @click="userRegister">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>

</template>

<script>
    export default {
        name: "Login",
        data(){
            return{
                //这是登录表单的数据绑定对象
                loginForm: {
                    username: '张三',
                    password: '123456',
                },
                loginRules:{
                    //验证用户名是否合法
                    username:[
                        {
                            require: true,message:'请输入用户名',trigger:'blur'
                        },
                        {
                            min:3,max:255,message: '长度在3到255个字符之间',trigger:'blur'
                        },
                    ],
                    //验证密码是否合法
                    password:[

                        {
                            min:6,max:15,message: '长度在6到15个字符串之间',trigger: 'blur'
                        },
                        {
                            require: true,message:'请输入密码',trigger:"blur"
                        }
                    ]
                }
            }
        },
        methods:{
            userLogin(){
                //预校验
                this.$refs.loginFormRef.validate(valid =>{
                    if(valid){
                        alert("成功");
                        axios.request({
                            url:'http://localhost:8181/login'
                        }).then(res=>{
                            console.log(res);
                        }).catch(err=>{
                            console.log(err);
                        })
                    }
                })
            },
            userRegister(){

            }
        },

    }
</script>

<style lang="less" scoped>
  el-input{
    background-color: gray;
  }
    .login_form{
      position: absolute;
      bottom: 0;
      width: 100%;
      padding: 0 20px;
      line-height: 60px;
      box-sizing: border-box;
    }
    .btns{
      display: flex;
      justify-content: flex-end;
    }
    .avatar_box{
        height:130px;
        width: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
      box-shadow: 0 0 10px #ddd;
      padding: 10px;
      position: absolute;
      left: 50%;
      transform: translate(-50%,-50%);
      background-color: #fff;
      img{
          width: 100%;
          height: 100%;
          border-radius: 50%;
          background-color: #eaffeb;
        }
    }
    #login-box{
        width: 450px;
        height: 300px;
        background-color: white;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
    }
</style>
