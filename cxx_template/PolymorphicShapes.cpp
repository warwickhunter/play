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

class Screen {
public:
    explicit Screen() {}
    virtual ~Screen() {}
};

class Shape {
public:
    explicit Shape() {}
    virtual ~Shape() {}
    virtual void draw(Screen& theScreen) const = 0;
};

class Point : public Shape {
public:
    explicit Point(int x, int y) : Shape(), x(x), y(y) {}
    virtual ~Point() {}

    virtual void draw(Screen& theScreen) const {
        cout << "Point::draw(x=" << x << ",y=" << y << ")" << endl;
    }

private:
    int x;
    int y;
};

class Line : public Shape {
public:
    explicit Line(int x1, int y1, int x2, int y2) : Shape(), x1(x1), y1(y1), x2(x2), y2(y2) {}
    virtual ~Line() {}

    virtual void draw(Screen& theScreen) const {
        cout << "Line::draw(x1=" << x1 << ",y1=" << y1 << ",x2=" << x2 << ",y2=" << y2 << ")" << endl;
    }

private:
    int x1;
    int y1;
    int x2;
    int y2;
};

int main(int argc, char** argv) {

    list<unique_ptr<Shape>> shapes;
    shapes.push_back(unique_ptr<Shape>(new Point(0,0)));
    shapes.push_back(unique_ptr<Shape>(new Point(10, 10)));
    shapes.push_back(unique_ptr<Shape>(new Line(0,0,10,10)));

    Screen theScreen;

    for (auto &x : shapes) {
        x->draw(theScreen);
    }

    return 0;
}

