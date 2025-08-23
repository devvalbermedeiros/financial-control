import React, { useState, useEffect, useCallback } from 'react';
import { getTransactions, getCategories, deleteTransaction } from '../api';
import { Container, Box, Typography, FormControl, InputLabel, Select, MenuItem, List, ListItem, ListItemText, Alert, Grid, IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';

const months = ["JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"];

const Transactions = () => {
    const [transactions, setTransactions] = useState([]);
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);
    const [year, setYear] = useState(new Date().getFullYear());
    const [month, setMonth] = useState(months[new Date().getMonth()]);

    const getUserIdFromToken = () => {
        const token = localStorage.getItem('token');
        if (!token) return null;
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            return JSON.parse(jsonPayload).sub;
        } catch (error) {
            console.error("Error decoding token:", error);
            return null;
        }
    };

    const fetchTransactions = useCallback(async () => {
        try {
            const userId = getUserIdFromToken();
            if (!userId) {
                setError('User not authenticated.');
                return;
            }
            const params = { userId, month, year };
            const response = await getTransactions(params);
            setTransactions(response.data);
        } catch (error) {
            setError('You do not have permission to view this page.');
        }
    }, [month, year]);

    useEffect(() => {
        fetchTransactions();
    }, [fetchTransactions]);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();
                setCategories(response.data);
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };
        fetchCategories();
    }, []);

    const handleDelete = async (id) => {
        try {
            await deleteTransaction(id);
            fetchTransactions();
        } catch (error) {
            console.error("Error deleting transaction:", error);
            setError('Error deleting transaction.');
        }
    };

    const handleYearChange = (e) => {
        setYear(parseInt(e.target.value));
    };

    const handleMonthChange = (e) => {
        setMonth(e.target.value);
    };

    const categoryMap = categories.reduce((acc, category) => ({ ...acc, [category.id]: category.name }), {});
    const currentYear = new Date().getFullYear();
    const years = Array.from({ length: 10 }, (_, i) => currentYear - i);

    return (
        <Container maxWidth="md">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Transactions
                </Typography>
                <Grid container spacing={2} sx={{ mb: 2 }}>
                    <Grid item xs={12} sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="month-select-label">Month</InputLabel>
                            <Select
                                labelId="month-select-label"
                                id="month-select"
                                value={month}
                                label="Month"
                                onChange={handleMonthChange}
                            >
                                {months.map(m => <MenuItem key={m} value={m}>{m}</MenuItem>)}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="year-select-label">Year</InputLabel>
                            <Select
                                labelId="year-select-label"
                                id="year-select"
                                value={year}
                                label="Year"
                                onChange={handleYearChange}
                            >
                                {years.map(y => <MenuItem key={y} value={y}>{y}</MenuItem>)}
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
                {error && <Alert severity="error">{error}</Alert>}
                <List>
                    {transactions.map((transaction) => (
                        <ListItem 
                            key={transaction.id} 
                            divider
                            secondaryAction={
                                <IconButton edge="end" aria-label="delete" onClick={() => handleDelete(transaction.id)}>
                                    <DeleteIcon />
                                </IconButton>
                            }
                        >
                            <ListItemText 
                                primary={transaction.description} 
                                secondary={`Amount: ${transaction.amount} | Category: ${categoryMap[transaction.categoryId] || 'Uncategorized'}`} 
                            />
                        </ListItem>
                    ))}
                </List>
            </Box>
        </Container>
    );
};

export default Transactions;