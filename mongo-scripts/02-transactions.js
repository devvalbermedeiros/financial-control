
// mongo-scripts/02-transactions.js

// Conecta-se ao banco de dados 'financial_control'
db = db.getSiblingDB('financial_control');

// Limpa as coleções para evitar duplicatas
db.revenues.deleteMany({});
db.debts.deleteMany({});

const userId = "user123"; // ID de usuário para os dados mockados

// --- Receitas ---
db.revenues.insertMany([
  {
    description: "Salário Mensal",
    amount: 5000.00,
    date: new Date(),
    userId: userId
  },
  {
    description: "Freelance Website",
    amount: 1200.50,
    date: new Date(new Date().setDate(new Date().getDate() - 15)), // 15 dias atrás
    userId: userId
  }
]);

print("Receitas inseridas com sucesso!");

// --- Dívidas ---

// Busca as categorias para referenciá-las
const catMoradia = db.categories.findOne({_id: "cat1"});
const catEducacao = db.categories.findOne({_id: "cat5"});
const catLazer = db.categories.findOne({_id: "cat4"});


db.debts.insertMany([
  // Dívida Parcelada (InstallmentDebt)
  {
    _class: "com.valber.financial_control.domain.entity.InstallmentDebt",
    type: "installment",
    description: "Compra Notebook",
    category: catEducacao,
    userId: userId,
    totalAmount: 4500.00,
    totalInstallments: 10,
    currentInstallment: 3,
    installmentAmount: 450.00,
    nextDueDate: new Date(new Date().getFullYear(), new Date().getMonth() + 1, 5) // Próximo mês, dia 5
  },
  // Dívida Recorrente (RecurringDebt)
  {
    _class: "com.valber.financial_control.domain.entity.RecurringDebt",
    type: "recurring",
    description: "Aluguel",
    category: catMoradia,
    userId: userId,
    amount: 1800.00,
    period: "MONTHLY",
    startDate: new Date(new Date().getFullYear(), 0, 10), // Início do ano
    endDate: null
  },
  // Dívida Recorrente (RecurringDebt)
  {
    _class: "com.valber.financial_control.domain.entity.RecurringDebt",
    type: "recurring",
    description: "Assinatura Streaming",
    category: catLazer,
    userId: userId,
    amount: 45.90,
    period: "MONTHLY",
    startDate: new Date(new Date().getFullYear(), new Date().getMonth(), 15), // Mês atual
    endDate: null
  }
]);

print("Dívidas inseridas com sucesso!");
