import React from 'react';
import { Link } from 'react-router-dom';
import { Container, Box, Typography, Button } from '@mui/material';

const Home = () => {
    return (
        <Container maxWidth="md">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography component="h1" variant="h4" gutterBottom>
                    Welcome to the Financial Control application.
                </Typography>
                <Typography variant="h6" color="text.secondary" paragraph>
                    Manage your finances with ease.
                </Typography>
                <Box sx={{ mt: 4 }}>
                    <Button variant="contained" component={Link} to="/transactions" sx={{ m: 1 }}>
                        View Transactions
                    </Button>
                    <Button variant="outlined" component={Link} to="/categories" sx={{ m: 1 }}>
                        Manage Categories
                    </Button>
                </Box>
            </Box>
        </Container>
    );
};

export default Home;