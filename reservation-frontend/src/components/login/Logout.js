import { useEffect } from "react";

export default function Logout({ onUnauthenticated }) {
  useEffect(() => {
    onUnauthenticated();
    window.location = '/login';
  });
  return <p>Saindo...</p>;
}