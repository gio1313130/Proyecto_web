import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRecursosComponent } from './admin-recursos.component';

describe('AdminRecursosComponent', () => {
  let component: AdminRecursosComponent;
  let fixture: ComponentFixture<AdminRecursosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRecursosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRecursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
