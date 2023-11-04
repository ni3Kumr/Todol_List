import './App.css'
import ListTodoComponent from './component/ListTodoComponent'
import HeaderComponent from './component/HeaderComponent'
import FooterComponent from './component/FooterComponent'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import TodoComonent from './component/TodoComonent'
import RegisterComponent from './component/RegisterComponent'
import LoginComponent from './component/LoginComponent'
import { isUserLoggedIn } from './services/AuthService'

function App() {

  
    function AuthenticatedRoute({children}){
     const isAuth= isUserLoggedIn();
     if(isAuth){
      return children;
     }
     else{
      return <Navigate to = '/' />
     }
    }
  


  return (
    <>
    <BrowserRouter>
    
    <HeaderComponent/>
    <Routes>
      <Route path='/' element={<LoginComponent/>}></Route>

      <Route path='/todos' element=
      {
      <AuthenticatedRoute>

        <ListTodoComponent/>
      </AuthenticatedRoute>
      }>

      </Route>
      {/* // http://localhost:3000/add-todo */}
      <Route path='/add-todo' element={
        <AuthenticatedRoute>
          <TodoComonent/>
        </AuthenticatedRoute>
      }>
        
      </Route>
      {/* Update todo */}
      <Route path='/edit-todo/:id' element=
      {
      <AuthenticatedRoute>

        <TodoComonent/>
      </AuthenticatedRoute>
      
      }></Route>
      <Route path='/register' element={<RegisterComponent/>}></Route>
      <Route path='/login' element ={<LoginComponent/>}></Route>
    </Routes>
     <FooterComponent/>
     
    </BrowserRouter>
    </>
  )
}

export default App
