import React, { useState } from 'react'
import { registerApiCall } from '../services/AuthService';

function RegisterComponent() {

  const [name , setName]  =useState('');
  const [username , setUsername]  =useState('');
  const [email , setEmail]  =useState('');
  const [password , setPassword]  =useState('');

  function registerUser(e){
    e.preventDefault();
    const register ={name,username,email,password}
    registerApiCall(register).then((response)=>{
        console.log(response.data);
    }).catch(error =>{

        console.error(error)});
    
    console.log(register);


  }
  return (
    <div className='container'>
        <br /><br />
        <div className='row'>
            <div className='col-md-6 offset-md-3'>
                <div className='card'>
                    <div className='card-header'>
                        <h2 className='text-center'>User Registration Form</h2>
                    </div>
                    <div className='card-body'>
                        <form >
                            <div className='row mb-3'>
                                <label className='col-md-3 control-lebel'>Name</label>
                                <div className='col-md-9'>
                                    <input type="text"
                                    name='name'
                                    className='form-control'
                                    placeholder='Enter Name'
                                    value={name}
                                    onChange={(e)=> setName(e.target.value)}
                                    />
                                </div>

                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-lebel'>Username</label>
                                <div className='col-md-9'>
                                    <input type="text"
                                    name='username'
                                    className='form-control'
                                    placeholder='Enter Username'
                                    value={username}
                                    onChange={(e)=> setUsername(e.target.value)}
                                    />
                                </div>

                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-lebel'>Email</label>
                                <div className='col-md-9'>
                                    <input type="email"
                                    name='email'
                                    className='form-control'
                                    placeholder='Enter Email'
                                    value={email}
                                    onChange={(e)=> setEmail(e.target.value)}
                                    />
                                </div>

                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-lebel'>Password</label>
                                <div className='col-md-9'>
                                    <input type="password"
                                    name='password'
                                    className='form-control'
                                    placeholder='Enter Password'
                                    value={password}
                                    onChange={(e)=> setPassword(e.target.value)}
                                    />
                                </div>

                            </div>
                            <div className='form-group mb-3'>

                            <button onClick={(e)=> registerUser(e)} className='btn btn-primary'>Register</button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>

        </div>


    </div>
  )
}

export default RegisterComponent