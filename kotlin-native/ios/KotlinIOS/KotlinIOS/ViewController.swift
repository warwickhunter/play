//
//  ViewController.swift
//  KotlinIOS
//
//  Created by Warwick Hunter on 4/5/19.
//  Copyright © 2019 Warwick Hunter. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        // This is actually calling Kotlin code defined in the SharedCode part of the Android project!
        label.text = CommonKt.createApplicationScreenMessage()
        view.addSubview(label)
    }
}

