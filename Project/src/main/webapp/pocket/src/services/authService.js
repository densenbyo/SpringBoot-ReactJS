import axios from "axios";
import jwt from 'jwt-decode';

const API_URL = "http://localhost:8080/users/";

class AuthService {
    login(username, password) {
        return axios.post(API_URL + "login", {username, password})
            .then(response => {
                if (response.data.accessToken) {
                    const token = response.data.accessToken;
                    const userToken = jwt(token);
                    console.log(userToken)
                    localStorage.setItem("userID", userToken['jti'])
                    localStorage.setItem("user", userToken['iss'])
                    localStorage.setItem("token", token)
                }
                return response.data;
            });

    }

    logout() {
        localStorage.removeItem("user");
        localStorage.removeItem("userID");
        localStorage.removeItem("token");
        setTimeout(()=>window.location.reload(),2000);
    }

    register(username, mail, password) {
        return axios.post(API_URL + "registration", {
            username,
            mail,
            password
        }).then(response=>{
            console.log(response.data);
        });
    }

    getCurrentUser() {
        return localStorage.getItem("user");
    }

    getRole(){
        return localStorage.getItem("userID");
    }

    changeRole(id, role){
        return axios.patch(API_URL + id + "/role/" + role)
            .then(() => {
                console.log("success");
                window.location.reload();
            })

    }

    block(id){
        return axios.patch(API_URL + id + "/block")
            .then(() => {
                console.log("success");
                window.location.reload();
            })
    }

    unblock(id){
        return axios.patch(API_URL + id + "/unblock")
            .then(() => {
                console.log("success");
                window.location.reload();
            })
    }

    setRemoved(id) {
        return axios.patch(API_URL+"allUsers/user_"+id+"/delete")
            .then(() => {
                window.location.reload();
            });
    }

    isLoggedIn() {
        return localStorage.length !== 0;
    }
}

export default new AuthService();