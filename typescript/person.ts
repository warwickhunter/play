// Mess around with interfaces, classes and property visibility 

interface Person {
    readonly firstName: string;
    readonly lastName: string;
    readonly age: number;
  }
  
  class DefaultPerson implements Person {
  
    readonly firstName: string;
    readonly lastName: string;
    readonly age: number;
    private hash: number;
  
    constructor(firstName: string, lastName: string, age: number) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.age = age;
      this.hash = firstName.length + lastName.length;
    }
  }
  
  function savePerson(person: Person): void {
      console.log('Saving ', person);
  }
  
  const p = new DefaultPerson("John", "Smith", 25);
  
  savePerson(p);