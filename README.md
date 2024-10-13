Checklist de Teste
1. Testes de Criação de Adotantes
Criar um adotante válido: Verificar se um adotante é criado com sucesso com dados válidos.✅
Criar um adotante com ID duplicado: Tentar criar um adotante com um ID que já existe e verificar se lança uma exceção.✅
Criar um adotante sem CPF: Tentar criar um adotante sem CPF e verificar se o sistema trata isso corretamente (exceção ou erro).
2. Testes de Obtenção de Adotantes
Obter adotante por ID existente: Verificar se um adotante pode ser recuperado com um ID válido.✅
Obter adotante por ID inexistente: Tentar obter um adotante com um ID que não existe e verificar se retorna mensagem. ✅
3. Testes de Edição de Adotantes
Editar um adotante existente: Verificar se um adotante pode ser atualizado com sucesso.✅
Editar um adotante não existente: Tentar editar um adotante com um ID que não existe e verificar se lança uma exceção.✅
4. Testes de Exclusão de Adotantes
Excluir um adotante existente: Verificar se um adotante pode ser excluído com sucesso. ✅
Excluir um adotante não existente: Tentar excluir um adotante que não existe e verificar se o sistema trata isso corretamente. ✅
5. Testes de Criação de Animais
Criar um animal válido: Verificar se um animal é criado com sucesso com dados válidos.✅
Criar um animal com ID duplicado: Tentar criar um animal com um ID que já existe e verificar se lança uma exceção.✅
Criar um animal com ID já adotado: Tentar criar um animal que já foi adotado e verificar se o sistema lança uma exceção.❌
6. Testes de Obtenção de Animais
Obter animal por ID existente: Verificar se um animal pode ser recuperado com um ID válido.✅
Obter animal por ID inexistente: Tentar obter um animal com um ID que não existe e verificar se retorna null ou lança uma exceção.✅
7. Testes de Edição de Animais
Editar um animal existente: Verificar se um animal pode ser atualizado com sucesso.✅
Editar um animal não existente: Tentar editar um animal com um ID que não existe e verificar se lança uma exceção.✅
8. Testes de Exclusão de Animais
Excluir um animal existente: Verificar se um animal pode ser excluído com sucesso.✅
Excluir um animal não existente: Tentar excluir um animal que não existe e verificar se o sistema trata isso corretamente.✅
9. Testes de Associação entre Animais e Adotantes
Adicionar um animal a um adotante existente: Verificar se um animal pode ser associado a um adotante corretamente. ✅
Adicionar um animal a um adotante não existente: Tentar adicionar um animal a um adotante que não existe e verificar se lança uma exceção.❌
10. Testes de Listagem de Animais e Adotantes
Listar todos os adotantes: Verificar se a lista de adotantes é recuperada corretamente.✅
Listar todos os animais: Verificar se a lista de animais é recuperada corretamente.✅
