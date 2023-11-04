import axios from "axios";
import { getToken } from "./AuthService";

const BASE_REST_API_URL='http://localhost:8080/api/todos';

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    config.headers['Authorization'] = getToken();
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });


export const getAllTodos = ()=> axios.get(BASE_REST_API_URL);
export const addTodo = (todo)=> axios.post(BASE_REST_API_URL,todo);
export const getTodoById = (todoId) => axios.get(BASE_REST_API_URL+'/'+todoId)
export const updateTodoById = (todoId,todo)=>axios.put(BASE_REST_API_URL+'/'+todoId,todo)



// delete Todo
export const deleteTodoById =(todoId)=> axios.delete(BASE_REST_API_URL+'/'+todoId);

// complete todo
export const completeTodo = (id)=> axios.patch(BASE_REST_API_URL+'/'+id+'/complete')
export const incompleteTodo = (id)=> axios.patch(BASE_REST_API_URL+'/'+id+'/in-complete')




