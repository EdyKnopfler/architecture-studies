import { Link } from "react-router-dom";

import './StepNavigator.scss';

export default function StepNavigator({ step }) {
  return (
    <nav className="StepNavigator clearFix">
      {
        step === 'going'
          ? <span>1. Voo (ida)</span>
          : <Link to="/flightGoing">1. Voo (ida)</Link>
      }
      {
        step === 'returning'
          ? <span>2. Voo (volta)</span>
          : <Link to="/flightReturning">2. Voo (volta)</Link>
      }
      {
        step === 'hotel'
          ? <span>3. Hotel</span>
          : <Link to="/hotel">3. Hotel</Link>
      }
      {
        step === 'payment'
          ? <span>4. Pagamento</span>
          : <Link to="/payment">4. Pagamento</Link>
      }
    </nav>
  );
}