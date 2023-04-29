import { useState } from 'react';

import { authenticate } from './loginService';
import './Login.scss';
import { Navigate } from 'react-router-dom';

export default function Login({ onAuthenticated }) {
  const [isLoggingIn, setLoggingIn] = useState(false);
  const [loginResult, setLoginResult] = useState({status: ''});
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  async function doLogin(event) {
    event.preventDefault();
    setLoggingIn(true);

    try {
      const user = await authenticate(email, password);

      if (user) {
        setLoginResult({status: 'ok'});
        onAuthenticated(user);
      } else {
        setLoginResult({status: 'invalid'});
      }
    } catch (error) {
      console.log(error);
      setLoginResult({status: 'error'});
    }
    finally {
      setLoggingIn(false);
    }
  }

  if (loginResult.status === 'ok') {
    return <Navigate to="/" replace={true} />
  }

  return (
    <div className="Login">
      <h1>Entre e garanta sua reserva</h1>
      {loginResult.status === 'error' &&
        <p className="loginError">
          Serviço indisponível, aguarde uns instantes e tente novamente.
        </p>
      }
      {loginResult.status === 'invalid' &&
        <p className="loginError">Dados de login incorretos.</p>
      }
      <form className="formDiv" onSubmit={doLogin}>
        <label>
          <span>E-mail</span>
          <input
            type="text" 
            onChange={(e) => setEmail(e.target.value)} />
        </label>
        <label>
          <span>Senha</span>
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)} />
        </label>
        <div className="buttonsDiv">
          <button disabled={isLoggingIn}>
            Entrar
          </button>
        </div>
      </form>
    </div>
  )
}