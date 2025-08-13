import axios from "axios";

// Set the base URL for the backend
const API = axios.create({
  baseURL: "http://localhost:8080", // Backend URL
});

export default API;
