import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Autores from './pages/Autores'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
      <h1>Library API</h1>
      <p>Sistema de gerenciamento de autores e livros</p>
      <Autores />
    </div>
  )
}

export default App
