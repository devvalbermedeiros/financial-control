import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Home from './components/Home';
import Users from './components/Users';
import Categories from './components/Categories';
import Transactions from './components/Transactions';
import CreateTransaction from './components/CreateTransaction';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';

const App = () => {
    const token = localStorage.getItem('token');

    const handleLogout = () => {
        localStorage.removeItem('token');
        window.location.href = '/login';
    };

    return (
        <Router>
            <div>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            Financial Control
                        </Typography>
                        {token && <Button color="inherit" component={Link} to="/">Home</Button>}
                        {token && <Button color="inherit" component={Link} to="/users">Users</Button>}
                        {token && <Button color="inherit" component={Link} to="/categories">Categories</Button>}
                        {token && <Button color="inherit" component={Link} to="/transactions">Transactions</Button>}
                        {token && <Button color="inherit" component={Link} to="/create-transaction">Create Transaction</Button>}
                        {token && <Button color="inherit" onClick={handleLogout}>Logout</Button>}
                    </Toolbar>
                </AppBar>
                <Routes>
                    <Route path="/login" element={token ? <Navigate to="/" /> : <Login />} />
                    <Route path="/" element={token ? <Home /> : <Navigate to="/login" />} />
                    <Route path="/users" element={token ? <Users /> : <Navigate to="/login" />} />
                    <Route path="/categories" element={token ? <Categories /> : <Navigate to="/login" />} />
                    <Route path="/transactions" element={token ? <Transactions /> : <Navigate to="/login" />} />
                    <Route path="/create-transaction" element={token ? <CreateTransaction /> : <Navigate to="/login" />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
