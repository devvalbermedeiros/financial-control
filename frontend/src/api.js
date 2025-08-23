import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1';

const api = axios.create({
    baseURL: API_URL,
});

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const login = (credentials) => api.post('/users/login', credentials);
export const getUsers = () => api.get('/users');
export const getCategories = () => api.get('/categories');
export const getTransactions = (params) => api.get('/transactions', { params });
export const createTransaction = (transactionData) => api.post('/transactions', transactionData);
export const deleteTransaction = (id) => api.delete(`/transactions/${id}`);

export default api;
