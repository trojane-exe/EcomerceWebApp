import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutofstockProductComponent } from './outofstock-product.component';

describe('OutofstockProductComponent', () => {
  let component: OutofstockProductComponent;
  let fixture: ComponentFixture<OutofstockProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OutofstockProductComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OutofstockProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
