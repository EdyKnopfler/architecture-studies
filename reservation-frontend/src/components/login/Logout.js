import { useEffect } from "react";
import { Navigate } from "react-router-dom";

export default function Logout({ onUnauthenticated }) {
  useEffect(() => {
    onUnauthenticated();
    window.location = '/login';
  });
  return <p>Saindo...</p>;
}