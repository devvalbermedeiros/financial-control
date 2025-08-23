import React, { useState, useEffect } from 'react';
import { getCategories } from '../api';
import { Container, Box, Typography, List, ListItem, ListItemText, Alert } from '@mui/material';

const Categories = () => {
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();
                setCategories(response.data);
            } catch (error) {
                setError('You do not have permission to view this page.');
            }
        };
        fetchCategories();
    }, []);

    return (
        <Container maxWidth="md">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Categories
                </Typography>
                {error && <Alert severity="error">{error}</Alert>}
                <List>
                    {categories.map(category => (
                        <ListItem key={category.id} divider>
                            <ListItemText primary={category.name} secondary={category.description} />
                        </ListItem>
                    ))}
                </List>
            </Box>
        </Container>
    );
};

export default Categories;