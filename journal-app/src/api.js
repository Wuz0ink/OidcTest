import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
});

api.interceptors.request.use(async (config) => {
  if (['post', 'put', 'delete'].includes(config.method)) {
    const csrfResponse = await axios.get('http://localhost:8080/csrf', { withCredentials: true });
    const csrfToken = csrfResponse.data.token;
    config.headers['X-XSRF-Token'] = csrfToken;
  }
  return config;
});

export default api;

