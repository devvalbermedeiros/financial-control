
// mongo-scripts/01-categories.js

// Conecta-se ao banco de dados 'financial_control'
db = db.getSiblingDB('financial_control');

// Limpa a coleção de categorias para evitar duplicatas
db.categories.deleteMany({});

// Insere as categorias
db.categories.insertMany([
  {
    _id: "cat1",
    name: "Moradia",
    description: "Despesas relacionadas à moradia, como aluguel, condomínio, etc."
  },
  {
    _id: "cat2",
    name: "Alimentação",
    description: "Despesas com supermercado, restaurantes, delivery."
  },
  {
    _id: "cat3",
    name: "Transporte",
    description: "Despesas com combustível, transporte público, aplicativos."
  },
  {
    _id: "cat4",
    name: "Lazer",
    description: "Despesas com entretenimento, como cinema, shows, passeios."
  },
  {
    _id: "cat5",
    name: "Educação",
    description: "Despesas com cursos, livros, mensalidades escolares."
  },
  {
    _id: "cat6",
    name: "Saúde",
    description: "Despesas com plano de saúde, farmácia, consultas."
  }
]);

print("Categorias inseridas com sucesso!");
