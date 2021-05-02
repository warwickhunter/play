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
  
  class OtherTypeOfPerson {

    static instancesCreated = 0;

    // constructor args with visibility keyword are automatically properties
    constructor(readonly firstName: string, readonly lastName: string, readonly age: number, private hash: number = 0) {
      this.hash = firstName.length + lastName.length;
      ++OtherTypeOfPerson.instancesCreated;
    }
  }

  function savePerson(person: Person): void {
      console.log('Saving ', person);
  }
  
  const p1 = new DefaultPerson("John", "Smith", 25);
  savePerson(p1);

  const p2 = new OtherTypeOfPerson("John", "Smith", 25);
  savePerson(p2);

  console.log(`instances ${OtherTypeOfPerson.instancesCreated}`)