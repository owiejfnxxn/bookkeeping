<template>
    <div id="login-box">
        <!-- 头像区域 -->
        <div class="avatar_box">
            <img src="../assets/logo.png" alt="">
        </div>
        <div>
            <el-form ref="loginFormRef" label-width="0px" class="login_form" :model="loginForm" :rules="loginRules">
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
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import router from "@/router";
    import {Message} from "element-ui";
    import axios from "axios";
    import Login from "@/views/Login";
    import {useUserStore} from "@/store";
    import * as userAPI from "@/api/userAPI"
    import * as settingsAPI from "@/api/settingsAPI";
    import {setStore, getStore} from "@/plugins/storage";

    axios.defaults.baseURL = "http://localhost:8181";
    export default {
        name: "Login",
        data() {
            return {
                //这是登录表单的数据绑定对象
                loginForm: {
                    username: 'wzh',
                    password: '123456',
                },
                loginRules: {
                    //验证用户名是否合法
                    username: [
                        {
                            require: true, message: '请输入用户名', trigger: 'blur'
                        },
                        {
                            min: 1, max: 255, message: '长度在1到255个字符之间', trigger: 'blur'
                        },
                    ],
                    //验证密码是否合法
                    password: [

                        {
                            min: 6, max: 15, message: '长度在6到15个字符串之间', trigger: 'blur'
                        },
                        {
                            require: true, message: '请输入密码', trigger: "blur"
                        }
                    ]
                }
            }
        },
        methods: {
            userLogin() {
                //预校验
                this.$refs.loginFormRef.validate(valid => {
                    if (valid) {
                        userAPI.login(this.loginForm.username, this.loginForm.password).then(res => {
                            if (res.data.success) {
                                Message.success("登录成功")
                                setStore('accessToken', res.data.data.token);
                                //获得用户数据
                                userAPI.userInfo().then(res => {
                                    let userInfo = res.data.data;
                                    //设置state数据
                                    useUserStore.state.id = userInfo.id;
                                    useUserStore.state.avatar = userInfo.avatar;
                                    useUserStore.state.totalExpenditure = userInfo.totalExpenditure;
                                    useUserStore.state.name = userInfo.name;
                                    useUserStore.state.nickname = userInfo.nickname;
                                    useUserStore.state.grossIncome = userInfo.grossIncome;
                                    //获取配置信息
                                    settingsAPI.getSettings().then(res => {
                                        let settings = res.data.data;
                                        this.$store.commit('updateSettings', settings)
                                        router.push('/user/index')
                                    }).catch(err => {
                                        console.log(err);
                                    })
                                }).catch(err => {
                                    console.log(err);
                                })
                            } else {
                                Message.error("登录失败，用户名或密码错误");
                            }
                        });
                    }
                })
            },
            userRegister() {
                router.push('/user/register')
            }
        },

    }
</script>

<style lang="less" scoped>
  el-input {
    background-color: gray;
  }

  .login_form {
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    line-height: 60px;
    box-sizing: border-box;
  }

  .btns {
    display: flex;
    justify-content: flex-end;
  }

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    box-shadow: 0 0 10px #ddd;
    padding: 10px;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eaffeb;
    }
  }

  #login-box {
    width: 450px;
    height: 300px;
    background-color: white;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
</style>
