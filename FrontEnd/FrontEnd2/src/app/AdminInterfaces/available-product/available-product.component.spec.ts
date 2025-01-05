import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableProductComponent } from './available-product.component';

describe('AvailableProductComponent', () => {
  let component: AvailableProductComponent;
  let fixture: ComponentFixture<AvailableProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailableProductComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AvailableProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
