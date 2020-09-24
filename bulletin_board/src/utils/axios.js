import Axios from 'axios';
import LocalSessionStorageService from "../services/LocalStorageService";
const localStorageService = LocalSessionStorageService.getService();

const axios = Axios.create({
    baseURL: "http://localhost:8080",
    crossdomain: true,
});

axios.interceptors.request.use(
    config => {
        const token = localStorageService.getAccessToken();
        if (token) {
            config.headers['Authorization'] = "Bearer "+token;
        }
        return config;
    },
    error => {
        Promise.reject(error);
    });

export default axios;