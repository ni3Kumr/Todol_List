import React, { useEffect } from 'react'
import { useState } from 'react'
import { completeTodo, deleteTodoById, getAllTodos, incompleteTodo } from '../services/TodoService';
import { useNavigate } from 'react-router-dom';
import { isAdminUser } from '../services/AuthService';
const ListTodoComponent = () => {

    const[todos ,setTodos] = useState([]);
    const navigate = useNavigate(); 
    const isAdmin = isAdminUser();

    // fetching data from backend
    useEffect(()=>{
    listTodos();

    },[])
    function listTodos(){
        getAllTodos().then(response=>{
            setTodos(response.data);
        }).catch(error=>{console.error(error)})


    }
    function addNewTodo(){
        navigate('/add-todo')

    }
    // update Todo
    function updateTodo(id){
        navigate(`/edit-todo/${id}`)

    }
    // delete Todo
    function deleteTodo(id){
        deleteTodoById(id).then(response=>{
            console.log(response.data)
            listTodos();
        }).catch(error=>{console.error(error)});


    }
    // completed Todo
    function completedTodo(id){
        completeTodo(id).then(response=>{
            console.log(response.data);
            listTodos();
        }).catch(error=>{console.error(error)});


    }
    // incomplete Todo
    function inCompletedTodo(id){
        incompleteTodo(id).then(response=>{
            console.log(response.data);
            listTodos();
        }).catch(error=>{console.error(error)});


    }
  return (
    <div className='container'>
        <h2 className='text-center'>List Of Todos</h2>
        {
              isAdmin && <button className='btn btn-primary' onClick={addNewTodo}>Add Todo</button>

        }

        <table className='table table-bordered table-striped'>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Completed</th>
                    <th>Action</th>

                </tr>
            </thead>
            <tbody>
                {
                    todos.map(todo =>
                    <tr key ={todo.id}>
                        <td>{todo.id}</td>
                        <td>{todo.title}</td>
                        <td>{todo.description}</td>
                        <td>{todo.completed ? 'Yes':'No'}</td>
                        <td>
                            {
                                isAdmin &&
                                <button className='btn btn-primary' onClick={()=>updateTodo(todo.id)} >Update</button>

                            }
                            {
                                isAdmin &&
                                <button className='btn btn-danger' onClick={()=>deleteTodo(todo.id)}  style={{marginLeft:'10px'}}>Delete</button>

                            }
                            <button className='btn btn-success' onClick ={()=>completedTodo(todo.id)} style={{marginLeft:'10px'}}>Complete</button>
                            <button className='btn btn-info' onClick ={()=>inCompletedTodo(todo.id)} style={{marginLeft:'10px'}}>In complete</button>
                        </td>
                    </tr>

                    )

                }
               
            </tbody>
        </table>


    </div>
  )
}

export default ListTodoComponent