import { Link } from "react-router-dom";

import './StepNavigator.scss';

export default function StepNavigator({ step }) {
  return (
    <nav className="StepNavigator">
      {
        step === 'flight'
          ? <span>1.Voo</span>
          : <Link to="/flight">1. Voo</Link>
      }
      {
        step === 'hotel'
          ? <span>2. Hotel</span>
          : <Link to="/hotel">2. Hotel</Link>
      }
      {
        step === 'payment'
          ? <span>3. Pagamento</span>
          : <Link to="/payment">3. Pagamento</Link>
      }
    </nav>
  );
}