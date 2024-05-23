import React, { useEffect, useState } from 'react';
import api from './api';
import Journal from './Journal';
import './App.css';

const App = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await api.get('/user', { withCredentials: true });
        setUser(response.data);
      } catch (error) {
        if (error.response && error.response.status === 401) {
          window.location.href = 'http://localhost:8080/oauth2/authorization/axis';
        } else {
          console.error('Error fetching user info:', error);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!user) {
    return <div>Redirecting to login...</div>;
  }

  return (
    <div>
      <h1>Welcome {user.givenName} {user.familyName}</h1>
      <Journal user={user} />
    </div>
  );
};

export default App;
