import React, { useState, useEffect } from 'react';
import { createTransaction, getCategories } from '../api';
import { Container, Box, Typography, TextField, Button, FormControl, InputLabel, Select, MenuItem, Alert } from '@mui/material';

const CreateTransaction = () => {
    const [description, setDescription] = useState('');
    const [amount, setAmount] = useState('');
    const [date, setDate] = useState(new Date().toISOString().slice(0, 10));
    const [categoryId, setCategoryId] = useState('');
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();
                setCategories(response.data);
            } catch (error) {
                setError('Error fetching categories.');
            }
        };
        fetchCategories();
    }, []);

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

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);
        try {
            const userId = getUserIdFromToken();
            if (!userId) {
                setError('User not authenticated.');
                return;
            }
            const transactionData = { description, amount: parseFloat(amount), date, categoryId, userId };
            await createTransaction(transactionData);
            setSuccess('Transaction created successfully!');
            setDescription('');
            setAmount('');
            setDate(new Date().toISOString().slice(0, 10));
            setCategoryId('');
        } catch (error) {
            setError('Error creating transaction.');
        }
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Create Transaction
                </Typography>
                <form onSubmit={handleSubmit}>
                    <TextField
                        label="Description"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                    <TextField
                        label="Amount"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />
                    <TextField
                        label="Date"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="category-select-label">Category</InputLabel>
                        <Select
                            labelId="category-select-label"
                            id="category-select"
                            value={categoryId}
                            label="Category"
                            onChange={(e) => setCategoryId(e.target.value)}
                            required
                        >
                            {categories.map(category => (
                                <MenuItem key={category.id} value={category.id}>
                                    {category.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
                    {success && <Alert severity="success" sx={{ mt: 2 }}>{success}</Alert>}
                    <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                        Create
                    </Button>
                </form>
            </Box>
        </Container>
    );
};

export default CreateTransaction;