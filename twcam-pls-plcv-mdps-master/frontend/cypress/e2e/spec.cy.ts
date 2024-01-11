describe("My First Test", () => {
  it("Visits the initial project page", () => {
    cy.visit("/");

    // login
    cy.get(".login").click();
    cy.get('input[name="nombre"]').type("cliente1");
    cy.get('input[name="password"]').type("cliente1");
    cy.get(".login-button").click();

    // navegar a crear reserva
    cy.get('a[routerlink="/eventos"]').click();
    cy.get('a[ng-reflect-router-link="/crear-reserva/2"]').click();
    
    // llenar invitados y enviar
    cy.get('input[ng-reflect-name="nombre"]').type("Samuel");
    cy.get('input[ng-reflect-name="dni"]').type("12345A");
    cy.get(".add-invitado").click();
    cy.get('input[ng-reflect-name="nombre"]').eq(1).type("Javi");
    cy.get('input[ng-reflect-name="dni"]').eq(1).type("12345B");
    cy.get(".add-invitado").click();
    cy.get('input[ng-reflect-name="nombre"]').eq(2).type("David");
    cy.get('input[ng-reflect-name="dni"]').eq(2).type("12345C");
    cy.get(".submit-button").click();
    
    // comprobar que los detalles de la reserva creada son correctos
    cy.get('a[routerlink="/reservas"]').click();
    cy.get("tr").eq(1).click();
    cy.get("tr").eq(1).contains('Pendiente de Pago')
    cy.get(".reserva-detail").eq(0).contains('Samuel')
    cy.get(".reserva-detail").eq(0).contains('Javi')
    cy.get(".reserva-detail").eq(0).contains('David')
    cy.get(".reserva-detail").eq(0).contains('12345A')
    cy.get(".reserva-detail").eq(0).contains('12345B')
    cy.get(".reserva-detail").eq(0).contains('12345C')
  });
});
