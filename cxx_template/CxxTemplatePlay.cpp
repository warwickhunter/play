#include <iostream>

using namespace std;

template<class T> class Container {

public:
    explicit Container(const T& item) : m_item(item) {}
    virtual ~Container() {}

    Container(const Container& rhs) {
        if (this != &rhs) {
            this->m_item = rhs.m_item;
        }
    }

    Container& operator=(const Container& rhs) {
        if (this != &rhs) {
            this->m_item = rhs.m_item;
        }
        return this;
    }

private:
    T m_item;
};

template <class T> Container<T> contain(const T& item) {
    return new Container<T>(item);
}

Container<int> contain(int & item) {
    return new Container<int>(item);
}


int main(int argc, char **argv) {
    cout << "Hello world" << endl;

    Container<const char*> stringContainer = contain<const char*>("abc");
    Container<int> intContainer = contain(123);

    return 0;
}
