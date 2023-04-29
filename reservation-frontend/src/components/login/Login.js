import './Login.scss';

export default function Login() {
  return (
    <div className="Login">
      <h1>Entre e garanta sua reserva</h1>
      <div className="formDiv">
        <label>
          <span>E-mail</span>
          <input id="email" type="text" />
        </label>
        <label>
          <span>Senha</span>
          <input id="password" type="password" />
        </label>
        <div className="buttonsDiv">
          <button>
            Entrar
          </button>
        </div>
      </div>
    </div>
  )
}