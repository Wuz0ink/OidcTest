import React, { useState, useEffect } from 'react';
import api from './api';
import './Journal.css';

const Journal = ({ user }) => {
  const [entries, setEntries] = useState([]);
  const [text, setText] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!user) {
      return;
    }

    const fetchEntries = async () => {
      try {
        const response = await api.get(`/journal`, { withCredentials: true });
        setEntries(response.data);
      } catch (error) {
        console.error('Error fetching journal entries:', error);
        setError(error);
      } finally {
        setLoading(false);
      }
    };

    fetchEntries();
  }, [user]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await api.post(`/journal`, { text }, { withCredentials: true });
      setText('');
      const response = await api.get(`/journal`, { withCredentials: true });
      setEntries(response.data);
    } catch (error) {
      console.error('Error posting journal entry:', error);
      setError(error);
    }
  };

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
    return new Date(dateString).toLocaleDateString('sv-SE', options);
  }

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error fetching data: {error.message}</div>;
  }

  return (
    <div>
      <form onSubmit={handleSubmit} className="Journal-form">
        <div className="Journal-input-container">
          <textarea
            value={text}
            onChange={(e) => setText(e.target.value)}
            maxLength={255}
            placeholder="Write your journal entry here..."
          />
          <button type="submit">Add Entry</button>
        </div>
      </form>
      {entries.length === 0 ? (
        <p>No journal entries found.</p>
      ) : (
        <ul className="Journal-entries">
          {entries.map((entry) => (
            <li key={entry.id}>
              <p>{entry.text}</p>
              <p>{formatDate(entry.date)}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Journal;
