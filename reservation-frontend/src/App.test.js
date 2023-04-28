import { render, screen } from '@testing-library/react';
import App from './App';

test('renders kkkkkkkk', () => {
  render(<App />);
  const linkElement = screen.getByText(/kkkkkkkk/i);
  expect(linkElement).toBeInTheDocument();
});
