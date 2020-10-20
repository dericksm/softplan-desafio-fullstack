export class ErrorsUtils {
  public static formatErrors(errosList) {
    const errors: string[] = [];
    errosList.forEach((err) => {
        console.log(err)
      errors.push(err.message);
    });
    return errors;
  }
}
