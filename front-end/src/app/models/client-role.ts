export enum ClientRole {
  ADMIN = "Admin",
  TRIADOR = "Triador",
  FINALIZADOR = "Finalizador",
}

export class ClientRoleHelper {
  public static translate(type: any): string {
    switch (type) {
      case 0:
        return "Admin";
      case 1:
        return "Triador";
      case 2:
        return "Finalizador";
      default:
        return "";
    }
  }
}
