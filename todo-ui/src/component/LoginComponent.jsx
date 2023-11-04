import React, { useState } from 'react'
import { loginUserApi, saveLoggedInUser, storeToken } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';

function LoginComponent() {
    const [usernameOrEmail, setUsernameOrEmail]=useState('');
    const [password, setPassword]=useState('');
    const navigator = useNavigate();

   async  function loginUser(e){
        e.preventDefault();
        // const login ={username,password};
    await loginUserApi(usernameOrEmail,password).then((response)=>{
            console.log(response.data);
            //convert username and password into base64 encryption
            // const token = 'Basic '+window.btoa(usernameOrEmail+":"+password);
            const token = 'Bearer '+response.data.accessToken;
            //role access
            const role = response.data.role;
            storeToken(token);
            saveLoggedInUser(usernameOrEmail,role);

            navigator('/todos')
            window.location.reload(false)

        }).catch(error => {console.error(error)});
        console.log(response); 


    }
  return (
    <div className='container'>
         <br /><br />
        <div className='row'>
            <div className='col-md-6 offset-md-3'>
                <div className='card'>
                    <div className='card-header'>
                        <h2 className='text-center'>Login Form</h2>
                    </div>
                    <div className='card-body'>
                        <form >
                            <div className='row mb-3'>
                                <label className='col-md-3 control-lebel'>Username Or Email</label>
                                <div className='col-md-9'>
                                    <input type="text"
                                    name='name'
                                    className='form-control'
                                    placeholder='Enter Userame Or Email'
                                    value={usernameOrEmail}
                                    onChange={(e)=> setUsernameOrEmail(e.target.value)}
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

                            <button onClick={(e)=> loginUser(e)} className='btn btn-primary'>Login</button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>

        </div>


    </div>
  )
}

export default LoginComponent