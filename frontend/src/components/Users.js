import React, { useState, useEffect } from 'react';
import { getUsers } from '../api';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await getUsers();
                setUsers(response.data);
            } catch (error) {
                setError('You do not have permission to view this page.');
            }
        };
        fetchUsers();
    }, []);

    return (
        <div>
            <h2>Users</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {users.map(user => (
                    <li key={user.id}>{user.username}</li>
                ))}
            </ul>
        </div>
    );
};

export default Users;
