export async function authenticate(email, password) {
  return new Promise((resolve, reject) => {
    // Por enquanto simulamos um serviço
    setTimeout(() => {
      if (Math.random() < 0.3) {
        return reject('Erro qualquer')
      }

      if (email === 'derso@minhacasa.com' && password === '123456') {
        resolve({
          name: 'Éderson Cássio',
          email
        });
      } else {
        resolve();
      }
    }, 1000);
  })
}