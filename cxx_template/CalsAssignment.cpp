/*
 * Messing around with polymorphism.
 *
 * @author Warwick Hunter
 * @since 2019-09-08
 */

#include <iostream>
#include <list>
#include <memory>

using namespace std;

class screen {
public:
    explicit screen() {}
    virtual ~screen() {}
};

class shape {
public:
    explicit shape() {}
    virtual ~shape() {}
    virtual void draw(screen& theScreen) const = 0;
};

class point : public shape {
public:
    explicit point(int x, int y) : shape(), x(x), y(y) {}
    virtual ~point() {}

    virtual void draw(screen& theScreen) const {
        cout << "point::draw(x=" << x << ",y=" << y << ")" << endl;
    }

private:
    int x;
    int y;
};

class line : public shape {
public:
    explicit line(int x1, int y1, int x2, int y2) : shape(), x1(x1), y1(y1), x2(x2), y2(y2) {}
    virtual ~line() {}

    virtual void draw(screen& theScreen) const {
        cout << "line::draw(x1=" << x1 << ",y1=" << y1 << ",x2=" << x2 << ",y2=" << y2 << ")" << endl;
    }

private:
    int x1;
    int y1;
    int x2;
    int y2;
};

int main(int argc, char** argv) {

    list<unique_ptr<shape>> shapes;
    shapes.push_back(unique_ptr<shape>(new point(0,0)));
    shapes.push_back(unique_ptr<shape>(new point(10, 10)));
    shapes.push_back(unique_ptr<shape>(new line(0,0,10,10)));

    screen theScreen;

    for (auto &x : shapes) {
        x->draw(theScreen);
    }

    return 0;
}

